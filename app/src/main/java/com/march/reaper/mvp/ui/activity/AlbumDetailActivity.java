package com.march.reaper.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.march.bean.Album;
import com.march.bean.AlbumItemCollection;
import com.march.reaper.R;
import com.march.reaper.common.DbHelper;
import com.march.reaper.common.TitleBarView;
import com.march.reaper.mvp.model.AlbumItemResponse;
import com.march.reaper.mvp.presenter.ActivityPresenter;
import com.march.reaper.mvp.presenter.impl.DetailCollectionPresenterImpl;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.mvp.presenter.impl.AlbumDetailPresenterImpl;
import com.march.reaper.mvp.ui.TitleActivity;
import com.march.reaper.utils.Lg;
import com.march.reaper.widget.RecyclerGroupView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 专辑详情界面
 */
public class AlbumDetailActivity extends TitleActivity {

    @Bind(R.id.detail_albumlist_rv)
    RecyclerGroupView mAlbumsRgv;
    private ActivityPresenter mPresenterImpl;
    public static final int TYPE_IS_COLLECTION = 0;
    public static final int TYPE_IS_DETAILS = 1;


    //展示收藏的图片
    public static void loadActivity4Collection(Activity activity) {
        Intent intent = new Intent(activity, AlbumDetailActivity.class);
        intent.putExtra(Constant.KEY_DETAIL_TYPE, TYPE_IS_COLLECTION);
        activity.startActivity(intent);
    }

    //展示某一个专辑的详情
    public static void loadActivity4DetailShow(Activity activity, Album album) {
        Intent intent = new Intent(activity, AlbumDetailActivity.class);
        intent.putExtra(Constant.KEY_DETAIL_TYPE, TYPE_IS_DETAILS);
        intent.putExtra(Constant.KEY_ALBUM_DETAIL_SHOW, album);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.detail_activity;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        if(getIntent().getIntExtra(Constant.KEY_DETAIL_TYPE,100) == TYPE_IS_DETAILS){
            mPresenterImpl = new AlbumDetailPresenterImpl(mAlbumsRgv, self);
            mPresenterImpl.setIntent(getIntent());
            mTitleBar.setText("首页", "专辑详情", "大图");
        }else if(getIntent().getIntExtra(Constant.KEY_DETAIL_TYPE,100) == TYPE_IS_COLLECTION){
            mPresenterImpl = new DetailCollectionPresenterImpl(mAlbumsRgv, self);
            mTitleBar.setText("我", "图片收藏", "大图");
        }
    }


    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        mAlbumsRgv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mPresenterImpl.justQuery();
    }


    @Override
    protected void initEvents() {
        super.initEvents();
        mTitleBar.setListener(TitleBarView.POS_Right, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenterImpl.switchMode((TextView) v);
            }
        });
    }
}
