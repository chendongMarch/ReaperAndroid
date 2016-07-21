package com.march.reaper.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.march.reaper.R;
import com.march.reaper.mvp.presenter.impl.AlbumCollPresenterImpl;
import com.march.reaper.mvp.ui.TitleActivity;
import com.march.reaper.widget.RecyclerGroupView;

import butterknife.Bind;

/**
 * 专辑收藏展示
 */
public class AlbumCollectionActivity extends TitleActivity
        implements AlbumCollPresenterImpl.AlbumCollView {

    @Bind(R.id.albumcol_recycler)
    RecyclerGroupView mAlbumRgv;
    private AlbumCollPresenterImpl mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.album_collection_activity;
    }

    @Override
    protected void destroyPresenter() {
        mPresenter = null;
    }

    public static void loadActivity(Activity activity) {
        activity.startActivity(new Intent(activity, AlbumCollectionActivity.class));
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mPresenter = new AlbumCollPresenterImpl(self);
        mPresenter.setRgv(
                mAlbumRgv
        );
    }

    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        mTitleBar.setText("我", "专辑收藏", null);
        mPresenter.justQuery();
    }
}
