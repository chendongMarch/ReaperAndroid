package com.march.reaper.base.fragment;

import android.content.Context;
import android.os.Bundle;

import com.march.reaper.base.mvp.presenter.BasePresenter;
import com.march.reaper.base.mvp.view.BaseView;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.base.fragment
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public abstract class BaseMVPFragment<P extends BasePresenter, V extends BaseView>
        extends BaseFragment
        implements BaseView {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    public void onInitDatas() {
        super.onInitDatas();
    }

    @Override
    public void onStartWorks() {
        super.onStartWorks();
        mPresenter = createPresenter();
        if (mPresenter != null)
            mPresenter.attachView((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
