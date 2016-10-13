package com.march.reaper.ipresenter.impl;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.march.bean.WholeAlbumItem;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.common.API;
import com.march.reaper.common.DbHelper;
import com.march.reaper.imodel.WholeAlbumResponse;
import com.march.reaper.ipresenter.BaseNetFragmentPresenter;
import com.march.reaper.iview.RootActivity;
import com.march.reaper.iview.activity.AlbumDetailActivity;
import com.march.reaper.utils.ColorUtils;
import com.march.reaper.utils.Lg;
import com.march.reaper.utils.QueryUtils;
import java.util.List;

/**
 * Created by march on 16/7/2.
 * 专辑页面
 */
public class AQ4WholePresenterImpl
        extends BaseNetFragmentPresenter<AlbumQueryPresenterImpl.AlbumQueryView, WholeAlbumItem>  {

    private boolean isBig = false;


    public AQ4WholePresenterImpl(AlbumQueryPresenterImpl.AlbumQueryView mView, RootActivity mContext) {
        super(mView, mContext);
    }

    @Override
    public void justQuery() {
        if (checkCanQuery())
            queryNetDatas();
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
        QueryUtils.get().query(API.GET_SCAN_WHOLE + "?offset=" + offset + "&limit=" + limit, WholeAlbumResponse.class, new QueryUtils.OnQueryOverListener<WholeAlbumResponse>() {
            @Override
            public void queryOver(WholeAlbumResponse rst) {
                List<WholeAlbumItem> data = rst.getData();
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
        mAdapter = new SimpleRvAdapter<WholeAlbumItem>(mContext, datas, R.layout.albumquery_item_album) {
            @Override
            public void bindData4View(RvViewHolder holder, WholeAlbumItem data, int pos) {
                holder.setImg(mContext, R.id.albumquery_item_iv, data.getAlbum_cover());
                if (isBig)
                    holder.setVisibility(R.id.albumquery_item_tv, View.VISIBLE).setText(R.id.albumquery_item_tv, data.getAlbum_desc());
                else
                    holder.setVisibility(R.id.albumquery_item_tv, View.GONE);
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
    public void switchMode() {
        isBig = !isBig;
        if (isBig) {
            mView.setModeTvText("小图");
            mRgv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        } else {
            mView.setModeTvText("大图");
            mRgv.setLayoutManager(new GridLayoutManager(mContext, 2));
        }
    }

}
