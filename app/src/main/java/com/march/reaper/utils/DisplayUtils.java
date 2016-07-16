package com.march.reaper.utils;

import android.content.Context;

import com.march.reaper.RootApplication;

/**
 * Created by march on 16/6/9.
 * 显示
 */
public class DisplayUtils {

    public static int getScreenWidth() {
        return RootApplication.get().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return RootApplication.get().getResources().getDisplayMetrics().heightPixels;
    }

    public  static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

}
