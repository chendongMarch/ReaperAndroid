package com.march.reaper.base.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.march.lib.core.activity.BaseActivity;
import com.march.lib.core.presenter.BasePresenter;
import com.march.lib.core.view.BaseView;
import com.march.reaper.base.mvp.life.PresenterLoader;
import com.march.reaper.common.Constant;

import butterknife.ButterKnife;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.base.activity
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public abstract class BaseLoaderMVPActivity
        <V extends BaseView, P extends BasePresenter>
        extends BaseActivity
        implements BaseView, LoaderManager.LoaderCallbacks<P> {

    //loader的id值
    private static final int LOADER_ID = 1000;
    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInitDatas() {
        super.onInitDatas();
        ButterKnife.bind(this);
    }

    @Override
    public void onStartWorks() {
        super.onStartWorks();
        //初始化loader
//        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }


//    @Override
//    public Loader<P> onCreateLoader(int id, Bundle args) {
//        return createPresenterLoader();
//    }
//

    protected abstract PresenterLoader<P> createPresenterLoader();

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null)
            mPresenter.attachView((V) this);
    }


    @Override
    public void onLoadFinished(Loader<P> loader, P data) {
        mPresenter = data;
    }

    @Override
    public void onLoaderReset(Loader<P> loader) {
        mPresenter = null;
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Bundle getData() {
        return getIntent().getBundleExtra(Constant.KEY_DEFAULT_DATA);
    }
}
