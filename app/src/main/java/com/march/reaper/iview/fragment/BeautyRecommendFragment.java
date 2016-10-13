package com.march.reaper.iview.fragment;

import com.march.reaper.R;
import com.march.reaper.base.fragment.BaseFragment;
import com.march.reaper.widget.RecyclerGroupView;

import butterknife.Bind;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.fragment
 * CreateAt : 2016/10/13
 * Describe : 全部推荐
 *
 * @author chendong
 */
public class BeautyRecommendFragment extends BaseFragment {

    @Bind(R.id.rgv)
    RecyclerGroupView mRgv;

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.beauty_recommend_fragment;
    }

    public static BeautyRecommendFragment newInst() {
        return new BeautyRecommendFragment();
    }


}
