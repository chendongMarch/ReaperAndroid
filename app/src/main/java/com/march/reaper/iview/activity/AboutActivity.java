package com.march.reaper.iview.activity;

import android.os.Bundle;
import android.view.View;

import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.base.mvp.life.PresenterLoader;
import com.march.lib.core.mvp.presenter.BasePresenter;

public class AboutActivity extends BaseReaperActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.about_activity;
    }

    @Override
    public void onInitViews(View view, Bundle save) {
        super.onInitViews(view,save);
        mTitleBarView.setText("我", "关于", null);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected String[] getPermission2Check() {
        return new String[0];
    }

    @Override
    protected boolean isInitTitle() {
        return true;
    }

}
