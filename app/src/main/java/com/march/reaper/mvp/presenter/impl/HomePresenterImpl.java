package com.march.reaper.mvp.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.march.bean.RecommendAlbumItem;
import com.march.bean.WholeAlbumItem;
import com.march.quickrvlibs.RvQuickAdapter;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.helper.RvConvertor;
import com.march.quickrvlibs.model.RvQuickModel;
import com.march.reaper.R;
import com.march.reaper.common.DbHelper;
import com.march.reaper.mvp.presenter.ActivityPresenter;

import java.util.List;

/**
 * Created by march on 16/6/30.
 */
public class HomePresenterImpl extends ActivityPresenter {

    private RecyclerView recyclerView;
    private Context context;
    private List<WholeAlbumItem> mWholeAlbumItems;
    private List<RecommendAlbumItem> mRecommendAlbumItems;
    private RvQuickAdapter<RvQuickModel> mWholeAlbumAdapter;
    private RvQuickAdapter<RvQuickModel> mRecommendAlbumAdapter;
    private int skip = 0;
    private int limit = 20;

    public HomePresenterImpl(Context context, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;
    }


    private void buildWholeAlbumList(List<WholeAlbumItem> list) {
        if (mWholeAlbumAdapter == null) {
            mWholeAlbumAdapter = new RvQuickAdapter<RvQuickModel>(context, RvConvertor.convert(list), R.layout.test_item_home_list) {

                @Override
                public void bindData4View(RvViewHolder holder, RvQuickModel data, int pos, int type) {

                }
            };
        }
        mWholeAlbumAdapter.notifyDataSetChanged();
    }

    private void buildRecommendAlbumList(List<RecommendAlbumItem> list) {
        if (mRecommendAlbumAdapter == null) {
            mRecommendAlbumAdapter = new RvQuickAdapter<RvQuickModel>(context, RvConvertor.convert(list), R.layout.test_item_home_list) {

                @Override
                public void bindData4View(RvViewHolder holder, RvQuickModel data, int pos, int type) {

                }
            };
        }
        mWholeAlbumAdapter.notifyDataSetChanged();
    }

    //分页加载所有的专辑
    public void queryWholeAlbum() {
        DbHelper.get().queryWholeAlbum(skip, limit, new DbHelper.OnQueryReadyListener<WholeAlbumItem>() {
            @Override
            public void queryReady(List<WholeAlbumItem> list) {
                if (list != null) {
                    skip += limit;
                    buildWholeAlbumList(list);
                }
            }
        });
    }

    //分页加载   所有推荐专辑 || 某类专辑
    public void queryAllRecommendAlbum(final String type) {
        DbHelper.get().queryAllRecommendAlbum(type, skip, limit, new DbHelper.OnQueryReadyListener<RecommendAlbumItem>() {
            @Override
            public void queryReady(List<RecommendAlbumItem> list) {
                if (list != null) {
                    skip += limit;
                    buildRecommendAlbumList(list);
                }
            }
        });
    }
}
