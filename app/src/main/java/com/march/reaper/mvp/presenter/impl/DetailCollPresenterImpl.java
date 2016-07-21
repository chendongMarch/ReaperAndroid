package com.march.reaper.mvp.presenter.impl;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import com.march.bean.DetailCollection;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.TypeRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.common.Constant;
import com.march.reaper.common.DbHelper;
import com.march.reaper.mvp.presenter.BaseNetActivityPresenter;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.mvp.ui.activity.ScanImgActivity;
import com.march.reaper.utils.ColorUtils;

import java.util.List;

/**
 * Created by march on 16/7/13.
 * 展示收藏的照片
 */
public class DetailCollPresenterImpl
        extends BaseNetActivityPresenter<AlbumDetailPresenterImpl.AlbumDetailView, DetailCollection> {

    private boolean isBig = false;

    public DetailCollPresenterImpl(RootActivity mContext) {
        super(mContext);
    }

    @Override
    protected void queryDbDatas() {
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

    }

    @Override
    public void justQuery() {

        if (checkCanQuery())
            queryDbDatas();
    }

    //构建adapter
    @Override
    protected void createRvAdapter() {
        mAdapter = new TypeRvAdapter<DetailCollection>(mContext, datas) {
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

        mAdapter.addHeaderOrFooter(0, R.layout.footer_load_more, mRgv.getRecyclerView());
        mAdapter.addType(DetailCollection.TYPE_SHU, R.layout.detail_item_show);
        mAdapter.addType(DetailCollection.TYPE_HENG, R.layout.detail_item_show2);
        mAdapter.addLoadMoreModule(mPreLoadNum, new LoadMoreModule.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                justQuery();
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                Intent intent = new Intent(mContext, ScanImgActivity.class);
                intent.putExtra(Constant.KEY_ALBUM_DETAIL_SCAN, datas.get(pos));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void switchMode() {
        isBig = !isBig;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        if (isBig) {
            mView.setModeTvText("小图");
        } else {
            mView.setModeTvText("大图");
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        mRgv.setLayoutManager(layoutManager);
        mRgv.setAdapter(mAdapter);
    }

    @Override
    public void setIntent(Intent intent) {

    }

}
