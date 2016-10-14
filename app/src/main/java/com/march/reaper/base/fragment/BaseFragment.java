package com.march.reaper.base.fragment;

import android.view.View;
import com.march.reaper.widget.TitleBarView;

/**
 * Created by march on 16/7/9.
 */
public abstract class BaseFragment extends ABsFragment {

    protected TitleBarView mTitleBarView;


    @Override
    protected View getLayoutView() {
        if (isInitTitle()) {
            mTitleBarView = new TitleBarView(mContext);
            getActivity().getLayoutInflater().inflate(getLayoutId(), mTitleBarView, true);
        }
        return mTitleBarView;
    }
}
