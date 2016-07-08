package com.march.reaper.mvp.ui.fragment;

import com.march.reaper.R;
import com.march.reaper.mvp.ui.RootFragment;

/**
 * 搜索展示
 */
public class SearchFragment extends RootFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.search_fragment;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mSelfName = SearchFragment.class.getSimpleName();
    }
}
