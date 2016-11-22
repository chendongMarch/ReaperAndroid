package com.march.reaper.imodel;

import com.march.reaper.common.API;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.helper.SharePreferenceHelper;

import io.reactivex.Flowable;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.imodel
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */
public class UserInfo {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserInfo(String userName) {
        this.userName = userName;
    }


    //使用deviceId注册
    public static void registerByDeviceId(String name, RequestCallback<BaseResponse> callback) {
        SharePreferenceHelper.get().putUserName(name);
        SharePreferenceHelper.get().putIsLogin(true);
        Flowable<BaseResponse> baseResponseFlowable = API.api().postRegister(new UserInfo(name));
        API.enqueue(baseResponseFlowable, callback);
    }

    //向服务器发开启记录
    public static void recordStartApp(RequestCallback<BaseResponse> callback) {
        String name = SharePreferenceHelper.get().getUserName();
        Flowable<BaseResponse> baseResponseFlowable = API.api().postRecord(new UserInfo(name));
        API.enqueue(baseResponseFlowable, callback);
    }

}
