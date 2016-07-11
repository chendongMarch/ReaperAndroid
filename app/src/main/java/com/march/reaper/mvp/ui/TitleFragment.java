package com.march.reaper.mvp.ui;

import android.view.View;
import android.view.ViewGroup;

import com.march.reaper.R;
import com.march.reaper.common.TitleBarView;

/**
 * Created by march on 16/7/9.
 */
public abstract class TitleFragment extends RootFragment {

    protected TitleBarView mTitleBar;

    @Override
    protected View getLayoutView() {
        ViewGroup rootView = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.widget_title_bar, null);
        getActivity().getLayoutInflater().inflate(getLayoutId(), rootView, true);
        mTitleBar = new TitleBarView(getActivity(),rootView);
        return rootView;
    }
}
