package com.march.lib_base.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by march on 16/7/16.
 * 应用相关
 */
public class AppHelper {

    /**
     * 是否是6.0以上版本
     *
     * @return 是否是6.0以上版本
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 当前应用版本号
     *
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取设备唯一id
     *
     * @return device id
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }


    public static HashMap<String, String> getUserInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        HashMap<String, String> params = new HashMap<>();
//        String deviceId = tm.getDeviceId();//获取智能设备唯一编号
//        String telephoneId = tm.getLine1Number();//获取本机号码
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        params.put("time", sdf.format(new Date(System.currentTimeMillis())));
        params.put("model", Build.MODEL);
//        params.put("deviceId", checkNotNull(deviceId));
//        params.put("telephoneId", checkNotNull(telephoneId));
        params.put("androidId", checkNotNull(androidId));
        for (String key : params.keySet()) {
            Logger.e(key + " ---> " + params.get(key));
        }
        return params;
    }

    private static String checkNotNull(String val) {
        return val == null || val.length() <= 0 ? "NoGet" : val;
    }
}
