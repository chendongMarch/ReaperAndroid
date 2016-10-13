package com.march.reaper.helper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Project  : CommonLib
 * Package  : com.march.baselib
 * CreateAt : 16/8/13
 * Describe : Toast工具类
 *
 * @author chendong
 */
public class Toaster {

//    @IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
//    private @interface Length {
//
//    }

    private static boolean isCanToast = true;
    private ToastBuilder mToastBuilder;
    private Handler mHandler;
    private Toast mToast;
    private static Toaster mInst;

    private Toaster() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取toaster单例
     *
     * @return Toaster
     */
    public static Toaster get() {
        if (mInst == null) {
            synchronized (Toaster.class) {
                if (mInst == null) {
                    mInst = new Toaster();
                }
            }
        }
        return mInst;
    }

    /**
     * 全局关闭toast
     *
     * @param isCanToast 是否可以toast
     */
    public static void setIsCanToast(boolean isCanToast) {
        Toaster.isCanToast = isCanToast;
    }

    /**
     * 显示短toast
     *
     * @param context context
     * @param msg     信息
     */
    public void show(Context context, String msg) {
        show(context, msg, 0, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短toast
     *
     * @param context context
     * @param strRes  字符串资源
     */
    public void show(Context context, int strRes) {
        show(context, null, strRes, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长toast
     *
     * @param context context
     * @param msg     信息
     */
    public void showLong(Context context, String msg) {
        show(context, msg, 0, Toast.LENGTH_LONG);
    }

    /**
     * 显示长toast
     *
     * @param context context
     * @param strRes  字符串资源
     */
    public void showLong(Context context, int strRes) {
        show(context, null, strRes, Toast.LENGTH_LONG);
    }

    /**
     * 初始化自定义ToastBuilder，实现自定义Toast
     *
     * @param builder 构建
     */
    public void initToastBulder(ToastBuilder builder) {
        this.mToastBuilder = builder;
    }

    /**
     * 构建toast
     */
    public interface ToastBuilder {
        Toast buildToast(Toast toast, String msg);
    }

    private void show(final Context context, String msg, int strRes, final int duration) {
        if (!isCanToast) return;
        if (msg == null) {
            //如果找不到,这里会跑出not found异常
            msg = context.getString(strRes);
        }
        final String finalMsg2Show = msg;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    if (mToastBuilder != null) {
                        mToast = new Toast(context);
                        mToastBuilder.buildToast(mToast, finalMsg2Show);
                        mToast.setDuration(duration);
                    } else {
                        mToast = Toast.makeText(context, finalMsg2Show, duration);
                    }
                } else {
                    mToast.setText(finalMsg2Show);
                    mToast.setDuration(duration);
                }
                mToast.show();
            }
        });
    }


}
