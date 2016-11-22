package com.march.reaper.iview.fragment;

import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.reaper.R;
import com.march.reaper.base.fragment.BaseReaperFragment;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.fragment
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public class HomeFunnyFragment extends BaseReaperFragment {
    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.funny_image_fragment;
    }

    public static HomeFunnyFragment newInst() {
        return new HomeFunnyFragment();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
