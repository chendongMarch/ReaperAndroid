package com.march.reaper.iview.fragment;

import com.march.reaper.R;
import com.march.reaper.base.fragment.BaseFragment;
import com.march.reaper.widget.RecyclerGroupView;

import butterknife.Bind;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.fragment
 * CreateAt : 2016/10/13
 * Describe : 全部专辑
 *
 * @author chendong
 */
public class BeautyWholeFragment extends BaseFragment {

    @Bind(R.id.rgv)
    RecyclerGroupView mRgv;

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.beauty_whole_fragment;
    }

    public static BeautyWholeFragment newInst() {
        return new BeautyWholeFragment();
    }


}
