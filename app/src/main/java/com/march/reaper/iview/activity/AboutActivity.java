package com.march.reaper.iview.activity;

import android.os.Bundle;

import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.base.mvp.life.PresenterLoader;

public class AboutActivity extends BaseReaperActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.about_activity;
    }

    @Override
    protected void onInitViews(Bundle save) {
        super.onInitViews(save);
        mTitleBarView.setText("我", "关于", null);
    }
    @Override
    protected String[] getPermission2Check() {
        return new String[0];
    }

    @Override
    protected boolean isInitTitle() {
        return true;
    }

    @Override
    protected PresenterLoader createPresenterLoader() {
        return null;
    }
}
