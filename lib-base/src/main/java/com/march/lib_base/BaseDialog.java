package com.march.lib_base;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Project  : CommonLib
 * Package  : com.march.baselib
 * CreateAt : 16/8/15
 * Describe : dialog基类
 *
 * @author chendong
 */
public abstract class BaseDialog extends AppCompatDialog {
    /**
     *  match_parent
     */
    protected int MATCH = ViewGroup.LayoutParams.MATCH_PARENT;
    /**
     * wrap_content
     */
    protected int WRAP = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 构造函数
     * @param context 上下文
     */
    public BaseDialog(Context context) {
        super(context);
        setContentView(getLayoutId());
        initViews();
        setWindowParams();
    }

    /**
     * 构造函数
     * @param context 上下文
     * @param theme 主题
     */
    public BaseDialog(Context context, int theme) {
        super(context, theme);
        setContentView(getLayoutId());
        initViews();
        setWindowParams();
    }

    protected abstract void initViews();

    protected abstract int getLayoutId();

    protected abstract void setWindowParams();

    protected void setWindowParams(int width, int height, float alpha, float dim, int gravity) {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // setContentView设置布局的透明度，0为透明，1为实际颜色,该透明度会使layout里的所有空间都有透明度，不仅仅是布局最底层的view
        params.alpha = alpha;
        // 窗口的背景，0为透明，1为全黑
        params.dimAmount = dim;
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        window.setAttributes(params);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    protected <V extends View> V getView(int id) {
        return (V) findViewById(id);
    }
}
