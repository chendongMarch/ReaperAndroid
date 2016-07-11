package com.march.reaper.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.march.reaper.R;
import com.march.reaper.mvp.ui.RootFragment;
import com.march.reaper.mvp.ui.TitleFragment;

/**
 * 搜索展示
 */
public class SearchFragment extends TitleFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.search_fragment;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mSelfName = SearchFragment.class.getSimpleName();
    }

    @Override
    protected void initViews(View view, Bundle save) {
        super.initViews(view, save);
        mTitleBar.setText(null, "我的", null);
    }
}
