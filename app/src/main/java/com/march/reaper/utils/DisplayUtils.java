package com.march.reaper.utils;

import com.march.reaper.ReaperApplication;

/**
 * Created by march on 16/6/9.
 *
 */
public class DisplayUtils {

    public static int getScreenWidth(){
        return ReaperApplication.get().getResources().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight(){
        return ReaperApplication.get().getResources().getDisplayMetrics().heightPixels;
    }

}
