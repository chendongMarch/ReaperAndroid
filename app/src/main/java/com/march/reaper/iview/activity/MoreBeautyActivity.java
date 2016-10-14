package com.march.reaper.iview.activity;

import android.support.v4.content.Loader;

import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.base.mvp.life.PresenterLoader;
import com.march.reaper.base.mvp.presenter.BasePresenter;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.activity
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */
public class MoreBeautyActivity extends BaseReaperActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

}
