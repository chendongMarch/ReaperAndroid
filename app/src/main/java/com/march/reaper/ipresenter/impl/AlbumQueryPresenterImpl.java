package com.march.reaper.ipresenter.impl;

import android.view.View;

import com.march.bean.RecommendAlbumItem;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.common.API;
import com.march.reaper.common.DbHelper;
import com.march.reaper.imodel.RecommendAlbumResponse;
import com.march.reaper.ipresenter.BaseNetFragmentPresenter;
import com.march.reaper.iview.BaseView;
import com.march.reaper.iview.RootActivity;
import com.march.reaper.iview.activity.AlbumDetailActivity;
import com.march.reaper.utils.ColorUtils;
import com.march.reaper.utils.QueryUtils;
import java.util.List;

/**
 * Created by march on 16/7/2.
 * 推荐页面
 */
public class AlbumQueryPresenterImpl
        extends BaseNetFragmentPresenter<AlbumQueryPresenterImpl.AlbumQueryView, RecommendAlbumItem> {

    private String mRecommendType;

    @Override
    public void switchMode() {

    }

    public interface AlbumQueryView extends BaseView {
        void setModeTvText(String txt);
    }

    public AlbumQueryPresenterImpl(AlbumQueryView mView, RootActivity mContext, String mRecommendType) {
        super(mView, mContext);
        this.mRecommendType = mRecommendType;
    }

    @Override
    public void justQuery() {
        if (checkCanQuery())
            queryNetDatas();
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
        final String url = API.GET_SCAN_RECOMMEND + "?offset=" + offset + "&limit=" + limit + "&albumtype=" + mRecommendType;
        QueryUtils.get().query(url, RecommendAlbumResponse.class, new QueryUtils.OnQueryOverListener<RecommendAlbumResponse>() {
            @Override
            public void queryOver(RecommendAlbumResponse rst) {
                List<RecommendAlbumItem> data = rst.getData();
                handleDatasAfterQueryReady(data);
            }

            @Override
            public void error(Exception e) {
                if (mAdapter != null)
                    mAdapter.finishLoad();
                mRgv.getPtrLy().refreshComplete();
                isLoadEnd = true;
            }
        });
    }


    //构建adapter
    @Override
    protected void createRvAdapter() {
        mAdapter = new SimpleRvAdapter<RecommendAlbumItem>(mContext, datas, R.layout.albumquery_item_album) {
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
                justQuery();
            }
        });
    }
}
