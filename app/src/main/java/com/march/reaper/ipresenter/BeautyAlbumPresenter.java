package com.march.reaper.ipresenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.march.bean.BeautyAlbum;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.inter.OnLoadMoreListener;
import com.march.quickrvlibs.module.HFModule;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.base.mvp.view.BaseRgvView;
import com.march.reaper.common.Constant;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.helper.CommonHelper;
import com.march.reaper.imodel.BeautyAlbumResponse;
import com.march.reaper.iview.activity.AlbumDetailActivity;
import com.march.reaper.widget.RecyclerGroupView;

import java.util.List;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.ipresenter
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public class BeautyAlbumPresenter
        extends NetLoadListPresenter
        <BeautyAlbumPresenter.BeautyRecommendView, BeautyAlbum> {
    private int mAlbumType;
    private String mRecommendAlbumType;

    public interface BeautyRecommendView extends BaseRgvView {

    }

    @Override
    protected RecyclerGroupView getRgv() {
        return mView.getRgv();
    }

    @Override
    public void attachView(BeautyRecommendView view) {
        super.attachView(view);
        getRgv().getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Bundle data = mView.getData();
        mAlbumType = data.getInt(Constant.KEY_BEAUTY_ALBUM_TYPE);
        mRecommendAlbumType = data.getString(Constant.KEY_BEAUTY_RECOMMEND_ALBUM_TYPE);
    }

    @Override
    public void justQuery() {
        if (checkCanQuery())
            queryNetDatas();
    }

    @Override
    protected void queryDbDatas() {

    }

    @Override
    protected void queryNetDatas() {
        RequestCallback<BeautyAlbumResponse> callback = new RequestCallback<BeautyAlbumResponse>() {
            @Override
            public void onSucceed(BeautyAlbumResponse rst) {
                List<BeautyAlbum> data = rst.getData();
                handleDatasAfterQueryReady(data);
            }

            @Override
            public void onError(Exception e) {
                if (mAdapter != null)
                    mAdapter.getLoadMoreModule().finishLoad();
                getRgv().getPtrLy().refreshComplete();
                isLoadEnd = true;
            }
        };
        if (mAlbumType == Constant.ALBUM_WHOLE) {
            BeautyAlbum.getWhole(offset, limit, callback);
        } else {
            BeautyAlbum.getRecommend(offset, limit, mRecommendAlbumType, callback);
        }

    }

    protected void createRvAdapter() {
        mAdapter = new SimpleRvAdapter<BeautyAlbum>(mView.getContext(), datas, R.layout.beauty_album_item) {
            @Override
            public void onBindView(RvViewHolder holder, BeautyAlbum data, int pos,int type) {
                holder.setImg(mView.getContext(), R.id.albumquery_item_iv, data.getAlbum_cover())
                        .setText(R.id.albumquery_item_tv, data.getAlbum_desc());
                holder.getView(R.id.album_bg).setBackgroundColor(CommonHelper.randomColor());
            }

            @Override
            public void onBindFooter(RvViewHolder footer) {
                super.onBindFooter(footer);

            }
        };
        HFModule hfModule = new HFModule(getContext(), HFModule.NO_RES, R.layout.common_footer_load_more, getRgv().getRecyclerView());
        mAdapter.addHFModule(hfModule);
        mAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                AlbumDetailActivity.loadActivity4DetailShow(getActivity(), datas.get(pos));
            }
        });
        mAdapter.addLoadMoreModule(new LoadMoreModule(mPreLoadNum, new OnLoadMoreListener() {
            @Override
            public void onLoadMore(LoadMoreModule mLoadMoreModule) {
                justQuery();
            }
        }));
    }

}
