package com.march.reaper.mvp.ui.activity;

import android.os.Bundle;

import com.march.reaper.R;
import com.march.reaper.mvp.ui.TitleActivity;

public class AboutActivity extends TitleActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.about_activity;
    }

    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        mTitleBar.setText("我", "关于", null);
    }
}
