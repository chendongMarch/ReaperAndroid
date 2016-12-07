package com.march.lib.platform.tencent;

import android.app.Activity;
import android.content.Intent;

import com.march.lib.platform.Platform;
import com.march.lib.platform.exception.PlatformException;
import com.march.lib.platform.helper.AuthTokenKeeper;
import com.march.lib.platform.helper.GsonUtil;
import com.march.lib.platform.listener.OnQQLoginListener;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.tencent
 * CreateAt : 2016/12/6
 * Describe :
 *
 * @author chendong
 */

public class QQLoginHelper {

    private Tencent mTencentApi;
    private Activity activity;
    private OnQQLoginListener onQQLoginListener;
    private LoginUiListener loginUiListener;


    public QQLoginHelper(Activity activity, Tencent mTencentApi, OnQQLoginListener onQQLoginListener) {
        this.activity = activity;
        this.mTencentApi = mTencentApi;
        this.onQQLoginListener = onQQLoginListener;
    }

    public void handleResultData(Intent data) {
        Tencent.handleResultData(data, this.loginUiListener);
    }

    public void login() {
        QQAccessToken qqToken = AuthTokenKeeper.getQQToken(activity);
        if (qqToken != null) {
            mTencentApi.setAccessToken(qqToken.getAccess_token(), qqToken.getExpires_in() + "");
            mTencentApi.setOpenId(qqToken.getOpenid());
            if (mTencentApi.isSessionValid()) {
                getUserInfo(qqToken);
            } else {
                loginUiListener = new LoginUiListener();
                mTencentApi.login(activity, "all", loginUiListener);
            }
        } else {
            loginUiListener = new LoginUiListener();
            mTencentApi.login(activity, "all", loginUiListener);
        }
    }

    private class LoginUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            JSONObject jsonResponse = (JSONObject) o;
            QQAccessToken qqToken = GsonUtil.getObject(jsonResponse.toString(), QQAccessToken.class);
            Platform.log("res ", qqToken.toString());
            // 保存token
            AuthTokenKeeper.saveQQToken(activity, qqToken);

            mTencentApi.setAccessToken(qqToken.getAccess_token(), qqToken.getExpires_in() + "");
            mTencentApi.setOpenId(qqToken.getOpenid());

            getUserInfo(qqToken);
        }


        @Override
        public void onError(UiError e) {
            onQQLoginListener.onException(new PlatformException("qq,获取用户信息失败", e));
        }

        @Override
        public void onCancel() {
            onQQLoginListener.onCancel();
        }
    }

    private void getUserInfo(QQAccessToken qqToken) {
        UserInfo info = new UserInfo(activity, mTencentApi.getQQToken());
        info.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                Platform.log("Get user info", object);
                QQUserInfo qqUserInfo = GsonUtil.getObject(object.toString(), QQUserInfo.class);

                if (onQQLoginListener != null) {
                    onQQLoginListener.onSucceed(qqUserInfo);
                }
            }

            @Override
            public void onError(UiError e) {
                onQQLoginListener.onException(new PlatformException("qq,获取用户信息失败", e));
            }

            @Override
            public void onCancel() {
                onQQLoginListener.onCancel();
            }

        });
    }
}
