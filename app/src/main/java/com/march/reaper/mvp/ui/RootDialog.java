package com.march.reaper.mvp.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * Created by march on 16/7/15.
 * dialog基类
 */
public abstract class RootDialog extends AppCompatDialog {


    protected static final int WRAP = -2;
    protected static final int MATCH = -1;

    public RootDialog(Context context) {
        super(context);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    public RootDialog(Context context, int theme) {
        super(context, theme);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    protected abstract int getLayoutId();

    protected abstract void setWindowParams();

    protected void setWindowParams(int width, int height, int gravity) {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
//        params.dimAmount = 1.0f;
        params.alpha = 1.0f;
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        window.setAttributes(params);
    }

}
