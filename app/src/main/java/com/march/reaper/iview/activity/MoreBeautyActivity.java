package com.march.reaper.iview.activity;

import android.app.Activity;

import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
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

    public static void startMoreBeauty(Activity activity) {

    }

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
        return R.layout.beauty_more_activity;
    }

}
