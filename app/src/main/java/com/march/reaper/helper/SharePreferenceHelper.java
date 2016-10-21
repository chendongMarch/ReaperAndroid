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
        editor = sp.edit();
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
        editor.putString(SP_APP_START_PHOTO, str);
        editor.apply();
    }

    public boolean getIsLogin() {
        return sp.getBoolean(SP_IS_LOGIN, false);
    }

    public void putIsLogin(boolean isLogin) {
        editor.putBoolean(SP_IS_LOGIN, isLogin);
        editor.apply();
    }

    public String getUserName() {
        return sp.getString(SP_USER_NAME,"");
    }

    public void putUserName(String name) {
        editor.putString(SP_USER_NAME, name);
        editor.apply();
    }
}
