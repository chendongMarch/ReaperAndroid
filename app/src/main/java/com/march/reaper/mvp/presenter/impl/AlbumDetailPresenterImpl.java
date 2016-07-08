package com.march.reaper.mvp.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.march.bean.Album;
import com.march.bean.AlbumDetail;
import com.march.bean.WholeAlbumItem;
import com.march.quickrvlibs.RvQuickAdapter;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.helper.RvConvertor;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.model.RvQuickModel;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.common.API;
import com.march.reaper.common.Constant;
import com.march.reaper.common.DbHelper;
import com.march.reaper.mvp.model.AlbumDetailResponse;
import com.march.reaper.mvp.model.WholeAlbumResponse;
import com.march.reaper.mvp.presenter.ActivityPresenter;
import com.march.reaper.mvp.ui.activity.ScanImgActivity;
import com.march.reaper.utils.ActivityAnimUtils;
import com.march.reaper.utils.DisplayUtils;
import com.march.reaper.utils.Lg;
import com.march.reaper.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by march on 16/7/2.
 * 详情
 */
public class AlbumDetailPresenterImpl extends ActivityPresenter {


    private static final int mPreLoadNum = Constant.PRE_LOAD_NUM;
    private View mHeadView;
    private Activity context;
    private RecyclerView mAlbumDetailRv;
    private List<AlbumDetail> datas;
    private RvQuickAdapter<AlbumDetail> mAlbumAdapter;
    private int mWidth;
    private int offset = 0, limit = Constant.ONECE_QUERY_DATA_NUM;
    private boolean isBig = false;
    private Album mAlbumData;

    public AlbumDetailPresenterImpl(Activity context, RecyclerView mAlbumDetailRv, View mHeadView, Album mAlbumData) {
        this.context = context;
        this.mAlbumDetailRv = mAlbumDetailRv;
        this.mHeadView = mHeadView;
        this.mAlbumData = mAlbumData;
        mWidth = DisplayUtils.getScreenWidth();
        datas = new ArrayList<>();
    }

    public void queryDatas() {
        if (offset == -1)
            return;
        DbHelper.get().queryAlbumDetail(mAlbumData.getAlbum_link(), offset, limit, new DbHelper.OnQueryReadyListener<AlbumDetail>() {
            @Override
            public void queryReady(List<AlbumDetail> list) {
                if (list.size() <= 0) {
                    offset = -1;
                    Lg.e("没有数据了");
                    return;
                }
                datas.addAll(list);
                if (mAlbumAdapter == null) {
                    createRvAdapter();
                    mAlbumDetailRv.setAdapter(mAlbumAdapter);
                } else {
                    mAlbumAdapter.notifyDataSetChanged();
                }
                //查询成功,offset增加
                offset += limit;
                mAlbumAdapter.finishLoad();
            }
        });
    }

    //从网络获取数据
    public void queryNetDatas() {
        if (offset == -1)
            return;
        StringBuilder sb = new StringBuilder(API.GET_SCAN_DETAIL).append("?offset=").append(offset).append("&limit=").append(limit).append("&albumlink=").append(mAlbumData.getAlbum_link());
        QueryUtils.get().query(sb.toString(), AlbumDetailResponse.class, new QueryUtils.OnQueryOverListener<AlbumDetailResponse>() {
            @Override
            public void queryOver(AlbumDetailResponse rst) {
                List<AlbumDetail> data = rst.getData();
                if(data.size()<=0){
                    offset = -1;
                    Lg.e("网络已经没有更多数据了");
                    return;
                }
                datas.addAll(data);
                if (mAlbumAdapter == null) {
                    createRvAdapter();
                    mAlbumDetailRv.setAdapter(mAlbumAdapter);
                } else {
                    mAlbumAdapter.notifyDataSetChanged();
                }
                //查询成功,offset增加
                offset += limit;
                mAlbumAdapter.finishLoad();
            }
        });
    }
    //构建adapter
    private void createRvAdapter() {
        mAlbumAdapter = new RvQuickAdapter<AlbumDetail>(context, datas) {
            @Override
            public void bindData4View(RvViewHolder holder, AlbumDetail data, int pos, int type) {
                holder.setImg(context, R.id.detail_item_show_iv, data.getPhoto_src());
            }

            @Override
            public void bindListener4View(RvViewHolder holder, int type) {
                super.bindListener4View(holder, type);
                ImageView iv = holder.getView(R.id.detail_item_show_iv);
                float rate;
                if (type == AlbumDetail.TYPE_SHU) {
                    rate = 1024 * 1.0f / 683;
                } else {
                    rate = 683 * 1.0f / 1024;
                }

                if (isBig) {
                    iv.getLayoutParams().height = (int) (mWidth * rate);
                } else {
                    iv.getLayoutParams().height = (int) (mWidth * rate * 0.5f);
                }
            }
        };

        mAlbumAdapter.addType(AlbumDetail.TYPE_SHU, R.layout.detail_item_show);
        mAlbumAdapter.addType(AlbumDetail.TYPE_HENG, R.layout.detail_item_show2);
        mAlbumAdapter.addLoadMoreModule(mPreLoadNum, new LoadMoreModule.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Lg.e("加载更多  " + offset);
                queryDatas();
            }
        });

        mAlbumAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                Intent intent = new Intent(context, ScanImgActivity.class);
                intent.putExtra(Constant.KEY_ALBUM_DETAIL_SCAN,datas.get(pos - mAlbumAdapter.getHeaderCount()));
                context.startActivity(intent);
                context.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        mAlbumAdapter.addHeaderOrFooter(mHeadView, null);
    }


    public void switchMode(TextView tv) {
        isBig = !isBig;
        if (isBig) {
            tv.setText("小图");
            mAlbumDetailRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        } else {
            tv.setText("大图");
            mAlbumDetailRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }
        mAlbumDetailRv.setAdapter(mAlbumAdapter);
    }

}
