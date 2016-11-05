package com.march.reaper.imodel;

import android.content.Context;

import com.march.lib.core.common.AppHelper;
import com.march.reaper.common.API;
import com.march.reaper.helper.RequestHelper;
import com.march.reaper.helper.SharePreferenceHelper;

import java.util.HashMap;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.imodel
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */
public class UserInfo {

    private void autoPostInfo(String url, HashMap<String, String> params) {
        RequestHelper.get().post(url, BaseResponse.class, params, null);
    }

    //使用deviceId注册
    public void registerByDeviceId(Context context, String name) {
        SharePreferenceHelper.get().putUserName(name);
        HashMap<String, String> userInfo = AppHelper.getUserInfo(context);
        userInfo.put("userName", name);
        SharePreferenceHelper.get().putIsLogin(true);
        autoPostInfo(API.POST_AUTO_REGISTER, userInfo);
    }

    //向服务器发开启记录
    public void recordStartApp(Context context) {
        HashMap<String, String> userInfo = AppHelper.getUserInfo(context);
        userInfo.put("userName", SharePreferenceHelper.get().getUserName());
        autoPostInfo(API.POST_AUTO_RECORD, userInfo);
    }
}
