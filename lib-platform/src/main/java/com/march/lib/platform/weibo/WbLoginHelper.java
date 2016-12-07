package com.march.lib.platform.weibo;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.march.lib.platform.exception.PlatformException;
import com.march.lib.platform.helper.Util;
import com.march.lib.platform.listener.OnWbLoginListener;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.weibo
 * CreateAt : 2016/12/5
 * Describe :
 *
 * @author chendong
 */

public class WbLoginHelper {

    private OnWbLoginListener loginListener;
    private Context context;
    private String appId;

    public WbLoginHelper(Context context, String appId) {
        this.context = context;
        this.appId = appId;
    }

    private void getUserInfo(Oauth2AccessToken mAccessToken) {
        //获取用户的信息
        UsersAPI mUsersAPI = new UsersAPI(context,
                appId, mAccessToken);
        mUsersAPI.show(Util.String2Long(mAccessToken.getUid()), new RequestListener() {
            @Override
            public void onComplete(String response) {
                if (!TextUtils.isEmpty(response)) {
                    WbUserInfo user = WbUserInfo.parse(response);
                    loginListener.onSucceed(user);
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                loginListener.onException(new PlatformException("wb,获取用户信息失败", e));
            }
        });
    }


    public void login(Activity activity, SsoHandler ssoHandler, final OnWbLoginListener loginListener) {
        this.loginListener = loginListener;
        if (loginListener == null)
            return;
        WbAuthHelper.auth(activity, ssoHandler, new WbAuthHelper.OnAuthOverListener() {
            @Override
            public void onAuth(Oauth2AccessToken token) {
                getUserInfo(token);
            }

            @Override
            public void onException(PlatformException e) {
                loginListener.onException(e);
            }

            @Override
            public void onCancel() {
                loginListener.onCancel();
            }
        });
    }
}
