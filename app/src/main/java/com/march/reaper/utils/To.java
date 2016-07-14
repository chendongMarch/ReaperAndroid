package com.march.reaper.utils;

import android.widget.Toast;

import com.march.reaper.RootApplication;

/**
 * Created by march on 16/7/8.
 * Toast
 */
public class To {

    public static void show(String msg) {
        Toast.makeText(RootApplication.get().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
