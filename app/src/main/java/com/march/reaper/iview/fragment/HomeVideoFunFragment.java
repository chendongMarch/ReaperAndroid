package com.march.reaper.iview.fragment;

import android.os.Bundle;
import android.view.View;

import com.march.lib_base.widget.TitleBarView;
import com.march.reaper.R;
import com.march.reaper.base.fragment.BaseReaperFragment;
import com.march.reaper.ipresenter.VideoFunPresenter;
import com.march.reaper.widget.RecyclerGroupView;

import butterknife.Bind;
/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.fragment
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */
public class HomeVideoFunFragment
        extends BaseReaperFragment<VideoFunPresenter>
        implements VideoFunPresenter.VideoFunView {
    @Bind(R.id.rgv)
    RecyclerGroupView mVideoRgv;

    @Override
    protected VideoFunPresenter createPresenter() {
        return new VideoFunPresenter();
    }

    @Override
    public void onInitViews(View view, Bundle saveData) {
        super.onInitViews(view, saveData);
        mTitleBarView.setText(TitleBarView.POS_Center, "视频");
    }

    @Override
    public void onStartWorks() {
        super.onStartWorks();
        mPresenter.justQuery();
    }

    @Override
    protected boolean isInitTitle() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.video_fragment;
    }

    public static HomeVideoFunFragment newInst() {
        return new HomeVideoFunFragment();
    }

    @Override
    public RecyclerGroupView getRgv() {
        return mVideoRgv;
    }


    @Override
    public void onPause() {
        super.onPause();
    }
}
