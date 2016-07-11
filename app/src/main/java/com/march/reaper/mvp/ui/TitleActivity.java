package com.march.reaper.mvp.ui;

import android.view.View;
import android.view.ViewGroup;

import com.march.reaper.R;
import com.march.reaper.common.TitleBarView;

/**
 * Created by march on 16/7/9.
 * 标题actiivty
 */
public abstract class TitleActivity extends RootActivity {

    protected TitleBarView mTitleBar;

    @Override
    protected View getLayoutView() {
        ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.widget_title_bar, null);
        getLayoutInflater().inflate(getLayoutId(), rootView, true);
        mTitleBar = new TitleBarView(this, rootView);
        return rootView;
    }
}
