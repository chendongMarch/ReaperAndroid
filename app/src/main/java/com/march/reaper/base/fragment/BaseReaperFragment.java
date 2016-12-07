package com.march.reaper.base.fragment;

import android.os.Bundle;
import android.view.View;

import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.lib.core.mvp.view.impl.BaseMVPFragment;
import com.march.reaper.helper.ActivityHelper;

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

    protected void animStart(){
        ActivityHelper.translateStart(getActivity());
    }

}
