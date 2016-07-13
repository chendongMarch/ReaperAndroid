package com.march.reaper.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.march.bean.AlbumItemCollection;
import com.march.reaper.R;
import com.march.reaper.mvp.presenter.ActivityPresenter;
import com.march.reaper.mvp.presenter.impl.AlbumCollectionPresenterImpl;
import com.march.reaper.mvp.ui.TitleActivity;
import com.march.reaper.widget.RecyclerGroupView;

import butterknife.Bind;

public class AlbumCollectionActivity extends TitleActivity {

    @Bind(R.id.albumcol_recycler)
    RecyclerGroupView mAlbumRgv;
    private ActivityPresenter mPresenterImpl;

    @Override
    protected int getLayoutId() {
        return R.layout.album_collection_activity;
    }

    public static void loadActivity(Activity activity) {
        activity.startActivity(new Intent(activity, AlbumCollectionActivity.class));
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mPresenterImpl = new AlbumCollectionPresenterImpl(self, mAlbumRgv);
    }

    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        mTitleBar.setText("我", "专辑收藏", null);
        mPresenterImpl.justQuery();
    }
}
