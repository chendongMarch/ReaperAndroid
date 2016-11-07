package com.march.reaper.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.march.reaper.base.ReaperApplication;

/**
 * Created by march on 16/7/2.
 * sp
 */
public class SharePreferenceHelper {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private final static String SP_NAME = "Reaper";
    private final static String SP_APP_START_PHOTO = "SP_APP_START_PHOTO";
    private final static String SP_IS_LOGIN = "SP_IS_LOGIN";
    private final static String SP_USER_NAME = "SP_USER_NAME";

    private SharePreferenceHelper() {
        sp = ReaperApplication.get().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    private static SharePreferenceHelper inst;

    public static SharePreferenceHelper get() {
        if (inst == null) {
            synchronized (SharePreferenceHelper.class) {
                if (inst == null) {
                    inst = new SharePreferenceHelper();
                }
            }
        }
        return inst;
    }

    public String getAppStartPhoto() {
        return sp.getString(SP_APP_START_PHOTO, null);
    }

    public void putAppStartPhoto(String str) {
        sp.edit().putString(SP_APP_START_PHOTO, str).apply();
    }

    public boolean getIsLogin() {
        return sp.getBoolean(SP_IS_LOGIN, false);
    }

    public void putIsLogin(boolean isLogin) {
        sp.edit().putBoolean(SP_IS_LOGIN, isLogin).apply();
    }

    public String getUserName() {
        return sp.getString(SP_USER_NAME, "");
    }

    public void putUserName(String name) {
        sp.edit().putString(SP_USER_NAME, name).apply();
    }
}
