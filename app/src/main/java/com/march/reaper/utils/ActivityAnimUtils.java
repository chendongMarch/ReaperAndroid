package com.march.reaper.utils;

/**
 * Created by march on 16/7/3.
 */

import android.app.Activity;
import android.content.Intent;

import com.march.reaper.R;

/**
 * 界面跳转动画，在跳转代码后调用
 */
public class ActivityAnimUtils {

    /**--------------------------------------------------右进右出---------------------------------------------------------**/
    /**
     * 开启新页面,并有从右边进来的动画
     *
     * @param activity
     * @param cls
     */
    public static void startActivityAndPullInRight(Activity activity, Class<?> cls) {
        activity.startActivity(new Intent(activity, cls));
        //已经在style中设置,这里就不用重复调用了
        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.no_fade);
    }

    /**
     * 开启新页面,并有从右边进来的动画
     *
     * @param activity
     * @param intentss
     */
    public static void startActivityAndPullInRight(Activity activity, Intent intent) {
        activity.startActivity(intent);
        //已经在style中设置,这里就不用重复调用了
        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.no_fade);
    }

    /**
     * 开启新页面,并有从右边进来的动画,并且关闭当前页面
     *
     * @param activity
     * @param cls
     */
    public static void startActivityAndPullInRightAndFinish(Activity activity, Class<?> cls) {
        activity.startActivity(new Intent(activity, cls));
        //已经在style中设置,这里就不用重复调用了
        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.no_fade);
        activity.finish();
    }

    /**
     * 关闭界面从右边出去
     *
     * @param activity
     */
    public static void finishActivityPushOutRight(Activity activity) {
        activity.finish();
        //已经在style中设置,这里就不用重复调用了
        activity.overridePendingTransition(R.anim.no_fade, R.anim.push_out_right);
    }

    /**
     * 关闭界面从右边出去
     *
     * @param activity
     */
    public static void pushOutRight(Activity activity) {
        activity.overridePendingTransition(R.anim.no_fade, R.anim.push_out_right);
    }

    /**
     * 左边出去右边进入
     *
     * @param activity
     */
    public static void pullRightPushLeft(Activity activity) {
        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    /**
     * 右边出去左边进入
     *
     * @param activity
     */
    public static void pullLeftPushRight(Activity activity) {
        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
/**--------------------------------------------------右进右出---------------------------------------------------------**/

/**--------------------------------------------------下进下出---------------------------------------------------------**/


    /**
     * 先放大后缩小，进出公用
     *
     * @param activity
     */
    public static void unzoomAnimation(Activity activity) {
        activity.overridePendingTransition(R.anim.unzoom_in, R.anim.unzoom_out);
    }
}
