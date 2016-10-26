package com.march.lib_base.common;

import android.util.Log;

/**
 * Project  : CommonLib
 * Package  : com.march.baselib
 * CreateAt : 16/8/15
 * Describe : 日志打印操作
 *
 * @author chendong
 */
public class Logger {

    //debug开关
    private static boolean DEBUG = true;
    //默认tag
    private static String TAG = "Logger";

    /**
     * 是否打印信息
     * @param debug 打印？
     */
    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    /**
     * 设置默认tag
     * @param defTag 默认tag
     */
    public static void setDefTag(String defTag) {
        TAG = defTag;
    }

    private static boolean checkMsgNotNull(Object msg) {
        if (msg == null) {
            Log.e(TAG, "log msg is null");
            return false;
        }
        return true;
    }

    private static boolean checkMsgNotNull(String tag, Object msg) {
        if (msg == null) {
            Log.e(getTag(tag), "log msg is null");
            return false;
        }
        return true;
    }

    private static String getTag(String tag) {
        return tag == null ? TAG : tag;
    }

    /**
     * 打印info信息
     * @param msg 信息
     */
    public static void i(Object msg) {
        if (DEBUG && checkMsgNotNull(msg)) {
            Log.i(TAG, msg.toString());
        }
    }

    /**
     * 打印info信息
     * @param tag tag
     * @param msg 信息
     */
    public static void i(String tag, Object msg) {
        if (DEBUG && checkMsgNotNull(tag, msg)) {
            Log.i(getTag(tag), msg.toString());
        }
    }

    /**
     * 打印error信息
     * @param msg 信息
     */
    public static void e(Object msg) {
        if (DEBUG && checkMsgNotNull(msg)) {
            Log.e(TAG, msg.toString());
        }
    }

    /**
     * 打印error信息
     * @param tag tag
     * @param msg 信息
     */
    public static void e(String tag, Object msg) {
        if (DEBUG && checkMsgNotNull(tag, msg)) {
            Log.e(getTag(tag), msg.toString());
        }
    }
}
