package com.march.reaper.iview.fragment;

import com.march.reaper.R;
import com.march.reaper.base.fragment.BaseFragment;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.fragment
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public class BeautyFragment extends BaseFragment {
    @Override
    protected boolean isInitTitle() {
        return false;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.beauty_fragment;
    }

    public static BeautyFragment newInst(){
        return new BeautyFragment();
    }
}
