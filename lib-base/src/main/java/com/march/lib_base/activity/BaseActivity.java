package com.march.lib_base.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


/**
 * Project  : CommonLib
 * Package  : com.march.baselib
 * CreateAt : 16/8/15
 * Describe : 向外公开的基类。实现部分简化操作的方法
 *
 * @author chendong
 */
public abstract class BaseActivity extends AbsActivityWrap {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 启动activity
     *
     * @param cls 被启动的activity
     */
    protected void startActivity(Class cls) {
        startActivity(new Intent(mContext, cls));
    }

    /**
     * 简化获取View
     *
     * @param id  view id
     * @param <V> View范型
     * @return 控件
     */
    protected <V extends View> V getView(int id) {
        return (V) findViewById(id);
    }

    /**
     * 设置监听事件
     * @param listener 事件
     * @param ids ids
     */
    protected void setClickListener(View.OnClickListener listener, int... ids) {
        for (int id : ids) {
            getView(id).setOnClickListener(listener);
        }
    }

    /**
     * 监测并显示dialog
     *
     * @param dialog 对话框
     * @return 返回true时表示已经显示，返回false表示没有初始化
     */
    protected boolean checkDialog2Show(Dialog dialog) {
        if (dialog == null)
            return false;
        if (!dialog.isShowing()) {
            dialog.show();
        }
        return true;
    }
}
