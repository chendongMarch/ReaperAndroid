package com.march.reaper.helper;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Project  : CommonLib
 * Package  : com.march.baselib
 * CreateAt : 16/8/13
 * Describe : 尺寸相关操作
 *
 * @author chendong
 */
public class DimensionHelper {

    private static DisplayMetrics metrics;


    private static DisplayMetrics getMetrics(Context context) {
        if (metrics == null) {
            metrics = context.getResources().getDisplayMetrics();
        }
        return metrics;
    }

    /**
     * 屏幕宽度
     *
     * @param context context
     * @param context context
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return getMetrics(context).widthPixels;
    }

    /**
     * 屏幕高度
     *
     * @param context context
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return getMetrics(context).heightPixels;
    }


    /**
     * dp转px
     *
     * @param context context
     * @param dpVal   dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getMetrics(context));
    }

    /**
     * sp转px
     *
     * @param context context
     * @param spVal   sp 值
     * @return px 值
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getMetrics(context));
    }

    /**
     * px转dp
     *
     * @param context context
     * @param pxVal   px 值
     * @return dp值
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = getMetrics(context).density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param context context
     * @param pxVal   px值
     * @return sp值
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / getMetrics(context).scaledDensity);
    }


}
