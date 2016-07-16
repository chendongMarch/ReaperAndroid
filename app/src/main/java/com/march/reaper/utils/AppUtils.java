package com.march.reaper.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.march.reaper.RootApplication;

/**
 * Created by march on 16/7/16.
 */
public class AppUtils {

    public static int getVersionCode() {
        try {
            PackageManager manager = RootApplication.get().getPackageManager();
            PackageInfo info = manager.getPackageInfo("com.march.reaper", 0);
//            String version = info.versionCode;
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
