package com.march.reaper.mvp.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.march.bean.Album;
import com.march.bean.DetailCollection;
import com.march.quickrvlibs.RvQuickAdapter;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.common.Constant;
import com.march.reaper.common.DbHelper;
import com.march.reaper.mvp.presenter.ActivityPresenter;
import com.march.reaper.mvp.ui.activity.ScanImgActivity;
import com.march.reaper.utils.ColorUtils;
import com.march.reaper.utils.DisplayUtils;
import com.march.reaper.utils.Lg;
import com.march.reaper.widget.RecyclerGroupView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by march on 16/7/13.
 * 展示收藏的照片
 */
public class DetailCollectionPresenterImpl extends ActivityPresenter {

    private List<DetailCollection> datas;
    private RvQuickAdapter<DetailCollection> mColAdapter;
    private int mWidth;
    private boolean isBig = false;

    public DetailCollectionPresenterImpl(RecyclerGroupView mRecyclerGV, Activity mContext) {
        super(mRecyclerGV, mContext);
        mWidth = DisplayUtils.getScreenWidth();
        datas = new ArrayList<>();
    }


    @Override
    protected void clearDatas() {
        datas.clear();
    }

    public void queryDbDatas() {
        DbHelper.get().queryDetailCollection(offset, limit, new DbHelper.OnQueryReadyListener<DetailCollection>() {
            @Override
            public void queryReady(List<DetailCollection> list) {
                handleDatasAfterQueryReady(list);
            }
        });
    }


    //从网络获取数据
    @Override
    protected void queryNetDatas() {
        queryDbDatas();
    }

    //处理加载后的数据
    private void handleDatasAfterQueryReady(List<DetailCollection> list) {
        if (list.size() <= 0) {
            offset = -1;
            Lg.e("没有数据了");
            return;
        }
        datas.addAll(list);
        if (mColAdapter == null) {
            createRvAdapter();
            mRecyclerGV.setAdapter(mColAdapter);
        } else {
            mColAdapter.notifyDataSetChanged();
        }
        //查询成功,offset增加
        offset += limit;
        mColAdapter.finishLoad();
        mRecyclerGV.getPtrLy().refreshComplete();
        isLoadEnd = true;
    }

    //构建adapter
    @Override
    protected void createRvAdapter() {
        mColAdapter = new RvQuickAdapter<DetailCollection>(mContext, datas) {
            @Override
            public void bindData4View(RvViewHolder holder, DetailCollection data, int pos, int type) {
                holder.setImg(mContext, R.id.detail_item_show_iv, data.getPhoto_src());
                View bgView = holder.getView(R.id.detail_show_bg);
                bgView.setBackgroundColor(ColorUtils.randomColor());
            }

            @Override
            public void bindListener4View(RvViewHolder holder, int type) {
                super.bindListener4View(holder, type);
                ImageView iv = holder.getView(R.id.detail_item_show_iv);
                float rate;
                if (type == DetailCollection.TYPE_SHU) {
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

        mColAdapter.addHeaderOrFooter(0, R.layout.footer_load_more, mRecyclerGV.getRecyclerView());
        mColAdapter.addType(DetailCollection.TYPE_SHU, R.layout.detail_item_show);
        mColAdapter.addType(DetailCollection.TYPE_HENG, R.layout.detail_item_show2);
        mColAdapter.addLoadMoreModule(mPreLoadNum, new LoadMoreModule.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Lg.e("加载更多  " + offset);
                justQuery();
            }
        });

        mColAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                Intent intent = new Intent(mContext, ScanImgActivity.class);
                intent.putExtra(Constant.KEY_ALBUM_DETAIL_SCAN, datas.get(pos));
                mContext.startActivity(intent);
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
            mRecyclerGV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }
        mRecyclerGV.setAdapter(mColAdapter);
    }



}
