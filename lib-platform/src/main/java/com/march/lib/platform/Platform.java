package com.march.lib.platform;

import android.content.Context;
import android.text.TextUtils;
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

    private WxPlatform wxPlatform;
    private QQPlatform qqPlatform;
    private WbPlatform wbPlatform;

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

    public void init(Context context,
                     String qqAppId,
                     String wxAppId,
                     String wbAppId) {
        if (!TextUtils.isEmpty(qqAppId))
            initQQ(context, qqAppId);
        if (!TextUtils.isEmpty(wxAppId))
            initWx(context, wxAppId);
        if (!TextUtils.isEmpty(wbAppId))
            initWb(context, wbAppId);
    }

    public static void log(String tag, Object msg) {
        Log.e(tag + "|platform", msg.toString());
    }


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
