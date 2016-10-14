package com.march.reaper.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

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

public abstract class BaseLifeFragment<P extends BasePresenter>
        extends BaseFragment
        implements BaseView {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract P createPresenterLoader();


    @Override
    public Context getContext() {
        return mContext;
    }


    public static final String KEY_DEFAULT_DATA = "KEY_DEFAULT_DATA";

    @Override
    public Intent getData() {
        Intent intent = new Intent();
        intent.putExtra(KEY_DEFAULT_DATA, getArguments());
        return intent;
    }
}
