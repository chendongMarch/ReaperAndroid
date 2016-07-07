package com.march.reaper.utils;

import android.util.Log;

/**
 * Created by march on 16/7/1.
 */
public class Lg {
    public static void e(Object msg) {
        if (msg == null)
            Log.e("Reaper", "msg is null");
        else
            Log.e("Reaper", msg.toString());
    }
}
