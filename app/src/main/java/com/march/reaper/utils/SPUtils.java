package com.march.reaper.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.march.bean.AlbumDetail;
import com.march.bean.RecommendAlbumItem;
import com.march.bean.WholeAlbumItem;
import com.march.reaper.base.ReaperApplication;

/**
 * Created by march on 16/7/2.
 * sp
 */
public class SPUtils {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private final static String SP_NAME = "Reaper";
    private final static String SP_APP_START_PHOTO = "SP_APP_START_PHOTO";
    private final static String SP_IS_LOGIN = "SP_IS_LOGIN";
    private final static String SP_USER_NAME = "SP_USER_NAME";

    private SPUtils() {
        sp = ReaperApplication.get().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    private static SPUtils inst;

    public static SPUtils get() {
        if (inst == null) {
            synchronized (SPUtils.class) {
                if (inst == null) {
                    inst = new SPUtils();
                }
            }
        }
        return inst;
    }

    private String mTempKey;

    private void generateTypeKey(Class cls) {
        new Operate4Type() {
            @Override
            protected void isAlbumDetail() {
                mTempKey = AlbumDetail.class.getSimpleName();
            }

            @Override
            protected void isRecommendAlbumItem() {
                mTempKey = RecommendAlbumItem.class.getSimpleName();
            }

            @Override
            protected void isWholeAlbumItem() {
                mTempKey = WholeAlbumItem.class.getSimpleName();
            }
        }.operate(cls);
    }

    public String getTimeStamp(Class cls) {
        generateTypeKey(cls);
        return sp.getString(mTempKey, "000000000000");
    }

    public void putTimeStamp(Class cls, String currentTimeStamp) {
        generateTypeKey(cls);
        editor.putString(mTempKey, currentTimeStamp).apply();
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
