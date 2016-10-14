package com.march.reaper.iview.fragment;

import com.march.reaper.R;
import com.march.reaper.base.fragment.BaseLifeFragment;
import com.march.reaper.ipresenter.BeautyRecommendPresenter;
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
public class BeautyRecommendFragment
        extends BaseLifeFragment<BeautyRecommendPresenter>
        implements BeautyRecommendPresenter.BeautyRecommendView {

    @Bind(R.id.rgv)
    RecyclerGroupView mRgv;

    @Override
    protected BeautyRecommendPresenter createPresenter() {
        return new BeautyRecommendPresenter();
    }

    @Override
    public void onStartWorks() {
        super.onStartWorks();
        mPresenter.justQuery();
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected BeautyRecommendPresenter createPresenterLoader() {
        return new BeautyRecommendPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.beauty_recommend_fragment;
    }


    public static BeautyRecommendFragment newInst() {
        return new BeautyRecommendFragment();
    }


    @Override
    public RecyclerGroupView getRgv() {
        return mRgv;
    }
}
