package com.march.reaper.iview.fragment;

import android.os.Bundle;

import com.march.reaper.R;
import com.march.reaper.base.fragment.BaseReaperFragment;
import com.march.reaper.common.Constant;
import com.march.reaper.ipresenter.BeautyAlbumPresenter;
import com.march.reaper.widget.RecyclerGroupView;

import butterknife.Bind;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.fragment
 * CreateAt : 2016/10/13
 * Describe : 推荐fragment,可以分类展示(viewpager + 多个fragments懒加载)
 *
 * @author chendong
 */
public class BeautyAlbumFragment
        extends BaseReaperFragment<BeautyAlbumPresenter>
        implements BeautyAlbumPresenter.BeautyAlbumView {

    @Bind(R.id.rgv)
    RecyclerGroupView mRgv;

    @Override
    protected BeautyAlbumPresenter createPresenter() {
        return new BeautyAlbumPresenter();
    }

    @Override
    public void onInitDatas() {
        super.onInitDatas();
        mSelfName = "beauty album fragment";
    }

    @Override
    public boolean forceLoad() {
        return false;
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
    protected int getLayoutId() {
        return R.layout.beauty_album_fragment;
    }


    public static BeautyAlbumFragment newInst(int albumType, String recommendType) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_BEAUTY_ALBUM_TYPE, albumType);
        bundle.putString(Constant.KEY_BEAUTY_RECOMMEND_ALBUM_TYPE, recommendType);
        BeautyAlbumFragment beautyAlbumFragment = new BeautyAlbumFragment();
        beautyAlbumFragment.setArguments(bundle);
        return beautyAlbumFragment;
    }

    @Override
    public RecyclerGroupView getRgv() {
        return mRgv;
    }

}
