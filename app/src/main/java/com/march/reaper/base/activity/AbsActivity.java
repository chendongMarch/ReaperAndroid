package com.march.reaper.base.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.march.reaper.base.ILife;
import com.march.reaper.common.Constant;

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
abstract class AbsActivity extends AppCompatActivity implements ILife {

    /**
     * oncreate 保存的bundle
     */
    protected Bundle mSaveBundle;
    /**
     * activity
     */
    protected Activity mActivity;
    /**
     * getApplicationContext()
     */
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = this;
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
        onInitIntent(getIntent().getBundleExtra(Constant.KEY_DEFAULT_DATA));
        onInitDatas();
        onInitViews(null, saveData);
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
//            if (getSupportActionBar() != null) {
//                getSupportActionBar().hide();
//            }
            if (getActionBar() != null) {
                getActionBar().hide();
            }
        }
    }

    @Override
    public void onInitIntent(Bundle intent) {

    }

    @Override
    public void onInitDatas() {

    }

    @Override
    public void onInitViews(View view, Bundle saveData) {

    }

    @Override
    public void onInitEvents() {

    }

    @Override
    public void onStartWorks() {

    }
}
