package com.march.reaper.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Project  : CommonLib
 * Package  : com.march.baselib
 * CreateAt : 16/8/15
 * Describe : 标准基类。负责activity流程化加载
 * 流程化加载周期
 * onInitIntent(Intent intent);
 * onInitDatas();
 * onInitViews(Bundle saveData);
 * onInitEvents();
 * onStartWorks();
 *
 * @author chendong
 */
abstract class AbsActivity extends AppCompatActivity {

    /**
     * oncreate 保存的bundle
     */
    protected Bundle mSaveBundle;
    /**
     * activity
     */
    protected AppCompatActivity mActivity;
    /**
     * getApplicationContext()
     */
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = getBaseContext();
        mSaveBundle = savedInstanceState;
        createViewShow();
        if (checkPermission())
            invokeCommonMethod(mSaveBundle);
    }

    /**
     * 监测权限
     *
     * @return 是否可以继续加载activity
     */
    protected abstract boolean checkPermission();

    /**
     * 加载完成开始逻辑工作
     */
    protected void onStartWorks() {
    }

    /**
     * 初始化事件监听
     */
    protected void onInitEvents() {
    }

    /**
     * 初始化控件
     *
     * @param save OnCreate中保存的bundle
     */
    protected void onInitViews(Bundle save) {
    }

    /**
     * 初始化数据
     */
    protected void onInitDatas() {
    }

    /**
     * 初始化 获取的Intent
     *
     * @param intent intent
     */
    protected void onInitIntent(Intent intent) {
    }

    /**
     * 是否全屏默认不全屏
     *
     * @return 是否全屏
     */
    protected boolean isFullScreen() {
        return false;
    }

    /**
     * 是否隐藏ActionBar默认隐藏
     *
     * @return 是否隐藏ActionBar默认隐藏
     */
    protected boolean isNoActionBar() {
        return true;
    }

    /**
     * 获取布局id,如果getLayoutView返回则不关心id
     *
     * @return layout id
     */
    protected abstract int getLayoutId();

    /**
     * 获取布局View,优先于getLayoutId
     *
     * @return 布局View
     */
    protected abstract View getLayoutView();

    /**
     * 执行公用方法
     *
     * @param saveData onCreate 保存的bundle
     */
    protected void invokeCommonMethod(Bundle saveData) {
        onInitIntent(getIntent());
        onInitDatas();
        onInitViews(saveData);
        onInitEvents();
        onStartWorks();
    }

    //创建界面
    private void createViewShow() {
        //全屏显示
        if (isFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (getLayoutView() != null)
            setContentView(getLayoutView());
        else
            setContentView(getLayoutId());
        hideActionBar();
    }


    //隐藏actionbar
    private void hideActionBar() {
        if (isNoActionBar()) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            if (getActionBar() != null) {
                getActionBar().hide();
            }
        }
    }
}
