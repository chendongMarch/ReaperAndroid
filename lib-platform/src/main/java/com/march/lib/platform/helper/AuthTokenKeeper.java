package com.march.lib.platform.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.march.lib.platform.R;
import com.march.lib.platform.tencent.QQAccessToken;
import com.march.lib.platform.wx.WxAccessToken;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.helper
 * CreateAt : 2016/12/6
 * Describe : 授权token存储管理
 *
 * @author chendong
 */

public class AuthTokenKeeper {

    public static final String TOKEN_STORE = "TOKEN_STORE";
    public static final String WX_TOKEN_KEY = "WX_TOKEN_KEY";
    public static final String WB_TOKEN_KEY = "WB_TOKEN_KEY";
    public static final String QQ_TOKEN_KEY = "QQ_TOKEN_KEY";

    private static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(TOKEN_STORE + context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public static WxAccessToken getWxToken(Context context) {
        SharedPreferences sp = getSp(context);
        return GsonUtil.getObject(sp.getString(WX_TOKEN_KEY, null), WxAccessToken.class);
    }

    public static void saveWxToken(Context context, WxAccessToken wxResponse) {
        SharedPreferences sp = getSp(context);
        String tokenJson = GsonUtil.getObject2Json(wxResponse);
        sp.edit().putString(WX_TOKEN_KEY, tokenJson).apply();
    }

    public static QQAccessToken getQQToken(Context context) {
        SharedPreferences sp = getSp(context);
        return GsonUtil.getObject(sp.getString(QQ_TOKEN_KEY, null), QQAccessToken.class);
    }

    public static void saveQQToken(Context context, QQAccessToken qqAccessToken) {
        SharedPreferences sp = getSp(context);
        String tokenJson = GsonUtil.getObject2Json(qqAccessToken);
        sp.edit().putString(QQ_TOKEN_KEY, tokenJson).apply();
    }

//    public static Oauth2AccessToken getWbToken(Context context) {
//        SharedPreferences sp = getSp(context);
//        return GsonUtil.getObject(sp.getString(WB_TOKEN_KEY, null), Oauth2AccessToken.class);
//    }
//
//    public static void saveWbToken(Context context, Oauth2AccessToken oauth2AccessToken) {
//        SharedPreferences sp = getSp(context);
//        String tokenJson = GsonUtil.getObject2Json(oauth2AccessToken);
//        sp.edit().putString(WB_TOKEN_KEY, tokenJson).apply();
//    }


    private static final String KEY_UID = "uid";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";

    public static void saveWbToken(Context context, Oauth2AccessToken token) {
        if (null == context || null == token) {
            return;
        }

        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_UID, token.getUid());
        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
        editor.putString(KEY_REFRESH_TOKEN, token.getRefreshToken());
        editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
        editor.apply();
    }

    public static Oauth2AccessToken getWbToken(Context context) {
        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = getSp(context);
        token.setUid(pref.getString(KEY_UID, ""));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
        token.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, ""));
        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));
        return token;
    }
}
