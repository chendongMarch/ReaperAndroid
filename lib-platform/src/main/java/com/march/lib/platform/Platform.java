package com.march.lib.platform;

import android.content.Context;
import android.util.Log;

import com.march.lib.platform.impl.QQPlatform;
import com.march.lib.platform.impl.WbPlatform;
import com.march.lib.platform.impl.WxPlatform;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform
 * CreateAt : 2016/12/3
 * Describe :
 *
 * @author chendong
 */

public class Platform {

    private String appName;

    private static Platform mInst;

    private Platform(Context context) {
        appName = context.getResources().getString(R.string.app_name);
    }

    public static void newInst(Context context) {
        if (mInst == null) {
            synchronized (Platform.class) {
                if (mInst == null) {
                    mInst = new Platform(context);
                }
            }
        }
    }

    public static Platform getInst() {
        return mInst;
    }

    public void initWx(Context context, String appId) {
        wxPlatform = new WxPlatform(context, appId, appName);
    }

    public void initQQ(Context context, String appId) {
        qqPlatform = new QQPlatform(context, appId, appName);
    }

    public void initWb(Context context, String appId) {
        wbPlatform = new WbPlatform(context, appId, appName);
    }

    public static void log(String tag, Object msg) {
        Log.e(tag + "|platform", msg.toString());
    }

    private WxPlatform wxPlatform;
    private QQPlatform qqPlatform;
    private WbPlatform wbPlatform;

    public WxPlatform wx() {
        return wxPlatform;
    }

    public WbPlatform wb() {
        return wbPlatform;
    }

    public QQPlatform qq() {
        return qqPlatform;
    }


}
