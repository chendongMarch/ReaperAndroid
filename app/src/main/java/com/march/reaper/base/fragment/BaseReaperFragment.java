package com.march.reaper.base.fragment;

import android.os.Bundle;
import android.view.View;

import com.march.lib_base.fragment.BaseMVPFragment;
import com.march.lib_base.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.base.fragment
 * CreateAt : 2016/10/26
 * Describe :
 *
 * @author chendong
 */

public abstract class BaseReaperFragment<P extends BasePresenter> extends BaseMVPFragment<P> {

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onInitViews(View view, Bundle saveData) {
        ButterKnife.bind(this, view);
        super.onInitViews(view, saveData);
    }
}
