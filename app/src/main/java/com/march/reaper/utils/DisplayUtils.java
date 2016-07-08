package com.march.reaper.utils;

import com.march.reaper.RootApplication;

/**
 * Created by march on 16/6/9.
 *
 */
public class DisplayUtils {

    public static int getScreenWidth(){
        return RootApplication.get().getResources().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight(){
        return RootApplication.get().getResources().getDisplayMetrics().heightPixels;
    }

}
