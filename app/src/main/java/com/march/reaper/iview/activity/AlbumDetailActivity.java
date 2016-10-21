package com.march.reaper.iview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.march.bean.BeautyAlbum;
import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.ipresenter.AlbumDetailPresenter;
import com.march.reaper.widget.RecyclerGroupView;
import com.march.reaper.widget.TitleBarView;

import butterknife.Bind;

/**
 * 专辑详情界面
 */
public class AlbumDetailActivity
        extends BaseReaperActivity<AlbumDetailPresenter>
        implements AlbumDetailPresenter.AlbumDetailView {

    @Bind(R.id.detail_albumlist_rv)
    RecyclerGroupView mRgv;
    public static final int TYPE_IS_COLLECTION = 0;
    public static final int TYPE_IS_DETAILS = 1;

    //展示收藏的图片
    public static void loadActivity4Collection(Activity activity) {
        Intent intent = new Intent(activity, AlbumDetailActivity.class);
        intent.putExtra(Constant.KEY_DETAIL_TYPE, TYPE_IS_COLLECTION);
        activity.startActivity(intent);
    }

    //展示某一个专辑的详情
    public static void loadActivity4DetailShow(Activity activity, BeautyAlbum album) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_DETAIL_TYPE, TYPE_IS_DETAILS);
        bundle.putParcelable(Constant.KEY_ALBUM_DETAIL_SHOW, album);
        Intent intent = buildIntent(activity, AlbumDetailActivity.class, bundle);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.detail_activity;
    }

    @Override
    public void onInitViews(View view, Bundle save) {
        super.onInitViews(view, save);
        mRgv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mTitleBarView.setText("首页", "专辑详情", "大图");
        mTitleBarView.setLeftBackListener(mActivity);
    }

    @Override
    public void onBackPressed() {
        animFinish();
    }

    @Override
    public void onStartWorks() {
        super.onStartWorks();
        mPresenter.justQuery();
    }

    @Override
    public void onInitEvents() {
        super.onInitEvents();
        mTitleBarView.setListener(TitleBarView.POS_Right, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.switchMode();
            }
        });
    }

    @Override
    public void setModeTvText(String txt) {
        mTitleBarView.get(TitleBarView.POS_Right).setText(txt);
    }

    @Override
    protected AlbumDetailPresenter createPresenter() {
        return new AlbumDetailPresenter();
    }

    @Override
    protected boolean isInitTitle() {
        return true;
    }

    @Override
    public RecyclerGroupView getRgv() {
        return mRgv;
    }
}
