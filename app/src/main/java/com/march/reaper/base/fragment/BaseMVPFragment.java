package com.march.reaper.base.fragment;

import android.content.Context;
import android.os.Bundle;

import com.march.reaper.base.mvp.presenter.BasePresenter;
import com.march.reaper.base.mvp.view.BaseView;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.base.fragment
 * CreateAt : 2016/10/13
 * Describe : fragment基类，主要负责处理MVP相关逻辑
 *
 * @author chendong
 */
public abstract class BaseMVPFragment<P extends BasePresenter>
        extends BaseFragment
        implements BaseView {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    public void onStartWorks() {
        super.onStartWorks();
        mPresenter = createPresenter();
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public Bundle getData() {
        return getArguments();
    }
}
