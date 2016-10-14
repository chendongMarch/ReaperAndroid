package com.march.reaper.iview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.base.mvp.life.PresenterFactory;
import com.march.reaper.base.mvp.life.PresenterLoader;
import com.march.reaper.ipresenter.impl.AlbumCollPresenterImpl;
import com.march.reaper.widget.RecyclerGroupView;

import butterknife.Bind;

/**
 * 专辑收藏展示
 */
public class AlbumCollectionActivity
        extends BaseReaperActivity<AlbumCollPresenterImpl>
        implements AlbumCollPresenterImpl.AlbumCollView {

    @Bind(R.id.albumcol_recycler)
    RecyclerGroupView mAlbumRgv;

    @Override
    protected int getLayoutId() {
        return R.layout.album_collection_activity;
    }


    public static void loadActivity(Activity activity) {
        activity.startActivity(new Intent(activity, AlbumCollectionActivity.class));
    }


    @Override
    public void onInitViews(View view, Bundle save) {
        super.onInitViews(view, save);
        mTitleBarView.setText("我", "专辑收藏", null);
        mAlbumRgv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mPresenter.justQuery();
    }

    @Override
    protected AlbumCollPresenterImpl createPresenter() {
        return new AlbumCollPresenterImpl();
    }

    @Override
    protected boolean isInitTitle() {
        return true;
    }

    @Override
    public RecyclerGroupView getRgv() {
        return mAlbumRgv;
    }
}
