package com.march.reaper.mvp.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.march.bean.RecommendAlbumItem;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.common.API;
import com.march.reaper.common.Constant;
import com.march.reaper.common.DbHelper;
import com.march.reaper.mvp.model.RecommendAlbumResponse;
import com.march.reaper.mvp.presenter.FragmentPresenter;
import com.march.reaper.mvp.ui.activity.AlbumDetailActivity;
import com.march.reaper.utils.ColorUtils;
import com.march.reaper.utils.DisplayUtils;
import com.march.reaper.utils.Lg;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.widget.RecyclerGroupView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by march on 16/7/2.
 * 推荐页面
 */
public class AlbumQueryPresenterImpl extends FragmentPresenter {

    private String mRecommendType;
    private List<RecommendAlbumItem> datas;
    private SimpleRvAdapter<RecommendAlbumItem> mAlbumAdapter;
    private int mWidth;


    public AlbumQueryPresenterImpl(Activity mContext, RecyclerGroupView mRecyclerGV, String mRecommendType) {
        super(mRecyclerGV, mContext);
        this.mRecommendType = mRecommendType;
        datas = new ArrayList<>();
        mWidth = DisplayUtils.getScreenWidth();
    }


    @Override
    protected void addAllData(List list) {
        datas.addAll(list);
    }

    @Override
    protected void clearDatas() {
        datas.clear();
    }

    //从数据库查询数据
    public void queryDbDatas() {
        DbHelper.get().queryAllRecommendAlbum(mRecommendType, offset, limit, new DbHelper.OnQueryReadyListener<RecommendAlbumItem>() {
            @Override
            public void queryReady(List<RecommendAlbumItem> list) {
                handleDatasAfterQueryReady(list);
            }
        });
    }

    //从网络访问数据
    @Override
    protected void queryNetDatas() {
        StringBuilder sb = new StringBuilder(API.GET_SCAN_RECOMMEND).append("?offset=").append(offset).append("&limit=").append(limit).append("&albumtype=").append(mRecommendType);
        QueryUtils.get().query(sb.toString(), RecommendAlbumResponse.class, new QueryUtils.OnQueryOverListener<RecommendAlbumResponse>() {
            @Override
            public void queryOver(RecommendAlbumResponse rst) {
                List<RecommendAlbumItem> data = rst.getData();
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

    //处理查询后的数据
    private void handleDatasAfterQueryReady(List<RecommendAlbumItem> list) {
        if (list.size() <= 0) {
            offset = -1;
            Lg.e("没有数据了");
            mAlbumAdapter.setFooterEnable(false);
            mAlbumAdapter.notifyDataSetChanged();
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
        mAlbumAdapter = new SimpleRvAdapter<RecommendAlbumItem>(mContext, datas, R.layout.albumquery_item_album) {
            @Override
            public void bindData4View(RvViewHolder holder, RecommendAlbumItem data, int pos) {
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
}
