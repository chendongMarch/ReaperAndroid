package com.march.reaper.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.march.reaper.base.ReaperApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by march on 16/7/16.
 * 应用相关
 */
public class AppUtils {

    public static int getVersionCode() {
        try {
            PackageManager manager = ReaperApplication.get().getPackageManager();
            PackageInfo info = manager.getPackageInfo("com.march.reaper", 0);
//            String version = info.versionCode;
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }


    public static HashMap<String, String> getUserInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        HashMap<String, String> params = new HashMap<>();
        String deviceId = tm.getDeviceId();//获取智能设备唯一编号
        String telephoneId = tm.getLine1Number();//获取本机号码
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        params.put("time", sdf.format(new Date(System.currentTimeMillis())));
        params.put("model", android.os.Build.MODEL);
        params.put("deviceId", checkNotNull(deviceId));
        params.put("telephoneId", checkNotNull(telephoneId));
        params.put("androidId", checkNotNull(androidId));
        for (String key : params.keySet()) {
            Lg.e(key + " ---> " + params.get(key));
        }
        return params;
    }

    private static String checkNotNull(String val) {
        return val == null || val.length() <= 0 ? "NoGet" : val;
    }
}
