package com.march.reaper.mvp.presenter.impl;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.march.bean.WholeAlbumItem;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.reaper.R;
import com.march.reaper.common.API;
import com.march.reaper.mvp.model.WholeAlbumResponse;
import com.march.reaper.mvp.presenter.FragmentPresenter;
import com.march.reaper.mvp.ui.activity.AlbumDetailActivity;
import com.march.reaper.utils.DisplayUtils;
import com.march.reaper.utils.QueryUtils;

import java.util.List;

/**
 * Created by march on 16/7/13.
 */
public class SearchPresenterImpl extends FragmentPresenter {

    private final int mWidth;
    private RecyclerView mAlbumRv;
    private List<WholeAlbumItem> datas;
    private SimpleRvAdapter<WholeAlbumItem> mAlbumAdapter;

    public SearchPresenterImpl(Activity mContext, RecyclerView mAlbumRv) {
        super(mContext);
        this.mAlbumRv = mAlbumRv;
        mWidth = DisplayUtils.getScreenWidth();
        this.mAlbumRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }


    @Override
    protected void clearDatas() {

    }

    @Override
    protected void queryDbDatas() {

    }

    @Override
    public void queryNetDatas() {
        QueryUtils.get().query(API.GET_LUCKY + "?limit=10", WholeAlbumResponse.class, new QueryUtils.OnQueryOverListener<WholeAlbumResponse>() {
            @Override
            public void queryOver(WholeAlbumResponse rst) {
                datas = rst.getData();
                createRvAdapter();
                isLoadEnd = true;
            }

            @Override
            public void error(Exception e) {

            }
        });
    }

    @Override
    protected void createRvAdapter() {
        mAlbumAdapter = new SimpleRvAdapter<WholeAlbumItem>(mContext, datas, R.layout.albumquery_item_album) {
            @Override
            public void bindData4View(RvViewHolder holder, WholeAlbumItem data, int pos) {
                int height = (int) (mWidth * (2f / 3f));
                holder.setImg(mContext, R.id.albumquery_item_iv, data.getAlbum_cover(), mWidth, height, R.mipmap.demo);
                holder.setVisibility(R.id.albumquery_item_tv, View.VISIBLE).setText(R.id.albumquery_item_tv, data.getAlbum_desc());
            }
        };
        mAlbumAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                AlbumDetailActivity.loadActivity4DetailShow(mContext, datas.get(pos));
            }
        });

        mAlbumRv.setAdapter(mAlbumAdapter);
    }
}