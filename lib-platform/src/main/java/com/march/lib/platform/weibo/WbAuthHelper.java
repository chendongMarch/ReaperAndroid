package com.march.lib.platform.weibo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.march.lib.platform.Platform;
import com.march.lib.platform.exception.PlatformException;
import com.march.lib.platform.helper.AuthTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.weibo
 * CreateAt : 2016/12/5
 * Describe :
 *
 * @author chendong
 */

public class WbAuthHelper {

    public interface OnAuthOverListener {
        void onAuth(Oauth2AccessToken token);

        void onException(PlatformException e);

        void onCancel();
    }

    public static void auth(Activity activity, SsoHandler mSsoHandler, OnAuthOverListener listener) {
        Oauth2AccessToken token = AuthTokenKeeper.getWbToken(activity);
        if (token != null && token.isSessionValid()) {
            listener.onAuth(token);
        } else {
            // 创建微博实例
            // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
            mSsoHandler.authorize(new MyWeiboAuthListener(activity, listener));
        }
    }


    private static class MyWeiboAuthListener implements WeiboAuthListener {
        OnAuthOverListener listener;
        Context context;

        public MyWeiboAuthListener(Context context, OnAuthOverListener listener) {
            this.context = context;
            this.listener = listener;
        }

        /**
         * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
         */
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            //String phoneNum = mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 授权成功
                AuthTokenKeeper.saveWbToken(context, mAccessToken);
                listener.onAuth(mAccessToken);
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                // 授权失败
                Platform.log("AuthListener", "授权失败 " + code);
                listener.onException(new PlatformException("授权失败 code = " + code));
            }
        }

        @Override
        public void onCancel() {
            // 授权取消
            listener.onCancel();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            // 授权失败
            Platform.log("AuthListener", "Auth exception : " + e.getMessage());
            listener.onException(new PlatformException("授权失败", e));
        }
    }
}
