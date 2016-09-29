package com.march.reaper.mvp.presenter.impl;

import android.content.Intent;

import com.march.reaper.common.API;
import com.march.reaper.common.SMSHelper;
import com.march.reaper.mvp.model.BaseResponse;
import com.march.reaper.mvp.presenter.BaseActivityPresenter;
import com.march.reaper.mvp.ui.BaseView;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.utils.QueryUtils;


import java.util.HashMap;

/**
 * com.march.reaper.mvp.presenter.impl
 * Created by chendong on 16/7/19.
 * desc : Register界面的Presenter
 */
public class RegisterPresenterImpl
        extends BaseActivityPresenter<RegisterPresenterImpl.RegisterView> {


    public RegisterPresenterImpl(RootActivity mContext) {
        super(mContext);
    }

    public interface RegisterView extends BaseView {
        void hideGetCodeButton();

        void registerToMyServer();

        void registerSucceed();

        void userAlreadyExist();

        void registerFailed();

        void getCodeSucceed();
    }

    /**
     * 注册短信验证码事件
     */
    public void registerEventHandler() {
        SMSHelper.get().registerHandler(new SMSHelper.SmsResultListener() {
            @Override
            public void onGetCodeSucceed() {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.hideGetCodeButton();
                    }
                });
            }

            @Override
            public void onSubmitCodeSucceed() {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.registerToMyServer();
                    }
                });
            }

            @Override
            public void onGetVoiceSucceed() {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.getCodeSucceed();
                    }
                });
            }

            @Override
            public void onError() {

            }
        });
    }


    /**
     * 验证码验证通过,向服务器注册
     */
    public void registerToMyServer(String phone, String pwd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", phone);
        map.put("pwd", pwd);
        QueryUtils.get().post(API.POST_ADD_USER, BaseResponse.class, map, new QueryUtils.OnQueryOverListener<BaseResponse>() {
            @Override
            public void queryOver(BaseResponse rst) {
                if (rst.getStatus() == 901) {
                    mView.userAlreadyExist();
                }
                if (rst.getStatus() == 902) {
                    mView.registerFailed();
                }
                if (rst.getStatus() == 0) {
                    authority();
                    mView.registerSucceed();
                }
            }

            @Override
            public void error(Exception e) {
                mView.registerFailed();
            }
        });
    }

    @Override
    public void setIntent(Intent intent) {

    }
}
