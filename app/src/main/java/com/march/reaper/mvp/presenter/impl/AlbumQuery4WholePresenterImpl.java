package com.march.reaper.mvp.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.march.bean.AlbumDetail;
import com.march.bean.RecommendAlbumItem;
import com.march.bean.WholeAlbumItem;
import com.march.bean.WholeAlbumItem;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.common.API;
import com.march.reaper.common.Constant;
import com.march.reaper.common.DbHelper;
import com.march.reaper.mvp.model.RecommendAlbumResponse;
import com.march.reaper.mvp.model.WholeAlbumResponse;
import com.march.reaper.mvp.presenter.ActivityPresenter;
import com.march.reaper.mvp.presenter.FragmentPresenter;
import com.march.reaper.mvp.ui.activity.AlbumDetailActivity;
import com.march.reaper.utils.DisplayUtils;
import com.march.reaper.utils.Lg;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.widget.RecyclerGroupView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by march on 16/7/2.
 * 专辑页面
 */
public class AlbumQuery4WholePresenterImpl extends FragmentPresenter {

    private List<WholeAlbumItem> datas;
    private SimpleRvAdapter<WholeAlbumItem> mAlbumAdapter;
    private int mWidth;
    private boolean isBig = false;

    public AlbumQuery4WholePresenterImpl(RecyclerGroupView mRecyclerGV, Activity mContext) {
        super(mRecyclerGV, mContext);
        datas = new ArrayList<>();
        mWidth = DisplayUtils.getScreenWidth();
    }


    @Override
    protected void clearDatas() {
        datas.clear();
    }

    //从数据库查询数据
    public void queryDbDatas() {
        DbHelper.get().queryWholeAlbum(offset, limit, new DbHelper.OnQueryReadyListener<WholeAlbumItem>() {
            @Override
            public void queryReady(List<WholeAlbumItem> list) {
                handleDatasAfterQueryReady(list);
            }
        });
    }


    //从网络获取数据
    @Override
    protected void queryNetDatas() {
        StringBuilder sb = new StringBuilder(API.GET_SCAN_WHOLE).append("?offset=").append(offset).append("&limit=").append(limit);
        QueryUtils.get().query(sb.toString(), WholeAlbumResponse.class, new QueryUtils.OnQueryOverListener<WholeAlbumResponse>() {
            @Override
            public void queryOver(WholeAlbumResponse rst) {
                List<WholeAlbumItem> data = rst.getData();
                handleDatasAfterQueryReady(data);
            }

            @Override
            public void error(Exception e) {
                if (mAlbumAdapter != null)
                    mAlbumAdapter.finishLoad();
                mRecyclerGV.getPtrLy().refreshComplete();
                isLoadEnd = true;
            }
        });
    }


    //处理加载后的数据
    private void handleDatasAfterQueryReady(List<WholeAlbumItem> list) {
        if (list.size() <= 0) {
            offset = -1;
            Lg.e("没有数据了");
            return;
        }
        datas.addAll(list);
        if (mAlbumAdapter == null) {
            createRvAdapter();
            mRecyclerGV.setAdapter(mAlbumAdapter);
        } else {
            mAlbumAdapter.notifyDataSetChanged();
        }
        //查询成功,offset增加
        offset += limit;
        mAlbumAdapter.finishLoad();
        mRecyclerGV.getPtrLy().refreshComplete();
        isLoadEnd = true;
    }

    //构建adapter
    @Override
    protected void createRvAdapter() {
        mAlbumAdapter = new SimpleRvAdapter<WholeAlbumItem>(mContext, datas, R.layout.albumquery_item_album) {
            @Override
            public void bindData4View(RvViewHolder holder, WholeAlbumItem data, int pos) {
                int height = (int) (mWidth * (2f / 3f));
                holder.setImg(mContext, R.id.albumquery_item_iv, data.getAlbum_cover(), mWidth, height, R.mipmap.demo);
                if (isBig)
                    holder.setVisibility(R.id.albumquery_item_tv, View.VISIBLE).setText(R.id.albumquery_item_tv, data.getAlbum_desc());
                else
                    holder.setVisibility(R.id.albumquery_item_tv, View.GONE);
            }

            @Override
            public void bindLisAndData4Footer(RvViewHolder footer) {
                footer.setClickLis(R.id.footer_loadmore, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        justQuery();
                    }
                });
            }
        };
        mAlbumAdapter.addHeaderOrFooter(0, R.layout.footer_load_more, mRecyclerGV.getRecyclerView());
        mAlbumAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                AlbumDetailActivity.loadActivity4DetailShow(mContext, datas.get(pos));
            }
        });
        mAlbumAdapter.addLoadMoreModule(mPreLoadNum, new LoadMoreModule.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Lg.e("加载更多  " + offset);
                justQuery();
            }
        });
    }


    public void switchMode(TextView tv) {
        isBig = !isBig;
        if (isBig) {
            tv.setText("小图");
            mRecyclerGV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        } else {
            tv.setText("大图");
            mRecyclerGV.setLayoutManager(new GridLayoutManager(mContext, 2));
        }
    }

}
