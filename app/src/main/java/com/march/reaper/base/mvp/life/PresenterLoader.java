package com.march.reaper.base.mvp.life;

import android.content.Context;
import android.support.v4.content.Loader;

import com.march.lib.core.presenter.BasePresenter;


/**
 * Project  : Reaper
 * Package  : com.march.reaper.base.mvp.life
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */
public class PresenterLoader<P extends BasePresenter> extends Loader<P> {

    private final PresenterFactory<P> mFactory;
    private P mPresenter;

    public PresenterLoader(Context context, PresenterFactory<P> factory) {
        super(context);
        this.mFactory = factory;
    }

    /**
     * 在Activity的onStart()调用之后
     */
    @Override
    protected void onStartLoading() {
        if (mPresenter != null) {
            deliverResult(mPresenter);//会将Presenter传递给Activity/Fragment。
            return;
        }
        forceLoad();
    }

    /**
     * 在调用forceLoad()方法后自动调用，我们在这个方法中创建Presenter并返回它。
     */
    @Override
    protected void onForceLoad() {
        mPresenter = mFactory.crate();//创建presenter
        deliverResult(mPresenter);
    }

    /**
     * 会在Loader被销毁之前调用，我们可以在这里告知Presenter以终止某些操作或进行清理工作。
     */
    @Override
    protected void onReset() {
        mPresenter = null;
    }
}
