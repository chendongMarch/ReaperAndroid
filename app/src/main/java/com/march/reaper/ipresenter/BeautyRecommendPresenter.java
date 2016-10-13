package com.march.reaper.ipresenter;

import android.view.View;

import com.march.bean.RecommendAlbumItem;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.base.mvp.view.BaseRgvView;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.helper.CommonHelper;
import com.march.reaper.helper.PageLoadHelper;
import com.march.reaper.imodel.RecommendAlbumResponse;
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

public class BeautyRecommendPresenter
        extends NetLoadListPresenter
        <BeautyRecommendPresenter.BeautyRecommendView, RecommendAlbumItem> {



    public interface BeautyRecommendView extends BaseRgvView {

    }

    @Override
    protected RecyclerGroupView getRgv() {
        return mView.getRgv();
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
        RecommendAlbumItem.queryRecommendAlbumForType(offset, limit, "all", new RequestCallback<RecommendAlbumResponse>() {
            @Override
            public void onSucceed(RecommendAlbumResponse rst) {
                List<RecommendAlbumItem> data = rst.getData();
                handleDatasAfterQueryReady(data);
            }

            @Override
            public void onError(Exception e) {
                if (mAdapter != null)
                    mAdapter.finishLoad();
                getRgv().getPtrLy().refreshComplete();
                isLoadEnd = true;
            }
        });
    }

    protected void createRvAdapter() {
        mAdapter = new SimpleRvAdapter<RecommendAlbumItem>(mView.getContext(), datas, R.layout.albumquery_item_album) {
            @Override
            public void bindData4View(RvViewHolder holder, RecommendAlbumItem data, int pos) {
                holder.setImg(mView.getContext(), R.id.albumquery_item_iv, data.getAlbum_cover())
                        .setText(R.id.albumquery_item_tv, data.getAlbum_desc());
                holder.getView(R.id.album_bg).setBackgroundColor(CommonHelper.randomColor());
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
        mAdapter.addHeaderOrFooter(0, R.layout.footer_load_more, getRgv().getRecyclerView());
        mAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                AlbumDetailActivity.loadActivity4DetailShow(getActivity(), datas.get(pos));
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
