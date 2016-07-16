package com.march.reaper.listener;

import android.widget.TextView;

import com.march.reaper.mvp.ui.RootDialog;

/**
 * Created by march on 16/7/16.
 * dialog点击监听事件
 */
public interface OnDialogBtnListener {
    void onBtnClick(RootDialog dialog, TextView btn);
}
