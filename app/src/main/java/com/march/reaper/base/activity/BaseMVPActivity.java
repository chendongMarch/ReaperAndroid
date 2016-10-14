package com.march.reaper.base.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.march.reaper.base.mvp.presenter.BasePresenter;
import com.march.reaper.base.mvp.view.BaseView;
import com.march.reaper.common.Constant;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.base.activity
 * CreateAt : 2016/10/14
 * Describe :
 *
 * @author chendong
 */

public abstract class BaseMVPActivity
        <P extends BasePresenter> extends BaseActivity
        implements BaseView {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    protected String[] getPermission2Check() {
        return new String[0];
    }

    @Override
    public void onInitDatas() {
        super.onInitDatas();
        mPresenter = createPresenter();
    }

    @Override
    public void onStartWorks() {
        super.onStartWorks();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    @Override
    public Bundle getData() {
        return getIntent().getBundleExtra(Constant.KEY_DEFAULT_DATA);
    }
}
