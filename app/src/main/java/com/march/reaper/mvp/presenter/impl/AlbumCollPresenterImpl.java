package com.march.reaper.mvp.presenter.impl;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.march.bean.AlbumItemCollection;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.common.DbHelper;
import com.march.reaper.mvp.presenter.BaseNetActivityPresenter;
import com.march.reaper.mvp.ui.BaseView;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.mvp.ui.activity.AlbumDetailActivity;
import com.march.reaper.utils.ColorUtils;
import com.march.reaper.utils.Lg;

import java.util.List;

/**
 * Created by march on 16/7/2.
 * 推荐页面
 */
public class AlbumCollPresenterImpl
        extends BaseNetActivityPresenter<AlbumCollPresenterImpl.AlbumCollView, AlbumItemCollection> {

    public AlbumCollPresenterImpl(RootActivity mContext) {
        super(mContext);
    }

    public interface AlbumCollView extends BaseView {

    }

    @Override
    public void justQuery() {
        if (checkCanQuery())
            queryDbDatas();
    }

    //从数据库查询数据
    public void queryDbDatas() {
        DbHelper.get().queryAlbumCollection(offset, limit, new DbHelper.OnQueryReadyListener<AlbumItemCollection>() {
            @Override
            public void queryReady(List<AlbumItemCollection> list) {
                handleDatasAfterQueryReady(list);
            }
        });
    }

    //从网络访问数据
    @Override
    protected void queryNetDatas() {

    }

    //构建adapter
    @Override
    protected void createRvAdapter() {
        mRgv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new SimpleRvAdapter<AlbumItemCollection>(mContext, datas, R.layout.albumquery_item_album) {
            @Override
            public void bindData4View(RvViewHolder holder, AlbumItemCollection data, int pos) {
                holder.setImg(mContext, R.id.albumquery_item_iv, data.getAlbum_cover())
                        .setText(R.id.albumquery_item_tv, data.getAlbum_desc());
                holder.getView(R.id.album_bg).setBackgroundColor(ColorUtils.randomColor());
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
        mAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                AlbumDetailActivity.loadActivity4DetailShow(mContext, datas.get(pos));
            }
        });
        mAdapter.addLoadMoreModule(mPreLoadNum, new LoadMoreModule.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Lg.e("加载更多  " + offset);
                justQuery();
            }
        });
    }

    @Override
    public void setIntent(Intent intent) {

    }

    @Override
    public void switchMode() {

    }
}
