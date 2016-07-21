package com.march.reaper.mvp.presenter.impl;

import android.content.Intent;

import com.march.reaper.common.API;
import com.march.reaper.mvp.model.BaseResponse;
import com.march.reaper.mvp.presenter.BaseActivityPresenter;
import com.march.reaper.mvp.ui.BaseView;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.mvp.ui.activity.HomePageActivity;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.utils.To;

import java.util.HashMap;

/**
 * com.march.reaper.mvp.presenter.impl
 * Created by chendong on 16/7/19.
 * desc : AppStart界面的Presenter
 */
public class LoginPresenterImpl
        extends BaseActivityPresenter<LoginPresenterImpl.LoginView> {


    public LoginPresenterImpl(RootActivity mContext) {
        super(mContext);
    }

    public interface LoginView extends BaseView {

    }

    //验证码验证通过,向服务器注册
    public void checkToMyServer(String phone, String pwd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", phone);
        map.put("pwd", pwd);
        QueryUtils.get().post(API.POST_CHECK_USER, BaseResponse.class, map, new QueryUtils.OnQueryOverListener<BaseResponse>() {
            @Override
            public void queryOver(BaseResponse rst) {
                if (rst.getStatus() == 903) {
                    To.show("用户名或密码不正确.");
                }
                if (rst.getStatus() == 0) {
                    To.show("登录成功");
                    authority();
                    mContext.startActivity(HomePageActivity.class);
                    mContext.finish();
                }
            }

            @Override
            public void error(Exception e) {
                To.show("登录失败");
            }
        });
    }

    @Override
    public void setIntent(Intent intent) {

    }

}
