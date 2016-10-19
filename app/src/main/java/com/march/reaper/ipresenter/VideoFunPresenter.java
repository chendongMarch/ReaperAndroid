package com.march.reaper.ipresenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.march.bean.VideoFun;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnLoadMoreListener;
import com.march.quickrvlibs.module.HFModule;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.base.mvp.view.BaseRgvView;
import com.march.reaper.common.Constant;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.helper.CommonHelper;
import com.march.reaper.helper.DimensionHelper;
import com.march.reaper.imodel.VideoResponse;
import com.march.reaper.widget.JCVideoPlayerStandard;
import com.march.reaper.widget.RecyclerGroupView;

import java.util.List;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.ipresenter
 * CreateAt : 2016/10/15
 * Describe :
 *
 * @author chendong
 */
public class VideoFunPresenter extends NetLoadListPresenter<VideoFunPresenter.VideoFunView, VideoFun> {


    public interface VideoFunView extends BaseRgvView {

    }

    @Override
    protected RecyclerGroupView getRgv() {
        return mView.getRgv();
    }

    @Override
    public void attachView(VideoFunView view) {
        super.attachView(view);
        getRgv().getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getRgv().getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Constant.IsVideoScrolled = false;
                } else {
                    Constant.IsVideoScrolled = true;
                }
            }
        });
    }

    @Override
    public void justQuery() {
        super.justQuery();
        if (checkCanQuery())
            queryNetDatas();
    }

    @Override
    protected void queryDbDatas() {

    }

    @Override
    protected void queryNetDatas() {
        RequestCallback<VideoResponse> requestCallback = new RequestCallback<VideoResponse>() {
            @Override
            public void onSucceed(VideoResponse rst) {
                List<VideoFun> data = rst.getData();
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
        VideoFun.getVideoFunData(offset, limit, requestCallback);
    }

    @Override
    protected void createRvAdapter() {
        mAdapter = new SimpleRvAdapter<VideoFun>(getContext(), datas, R.layout.video_item_show) {
            @Override
            public void onBindView(RvViewHolder holder, VideoFun data, int pos, int type) {
                super.onBindView(holder, data, pos, type);
                ViewGroup.LayoutParams layoutParams = holder.getParentView().getLayoutParams();
                int itemWidth = DimensionHelper.getScreenWidth(getContext());
                int itemHeight = (int) (itemWidth * 1.0f * data.getHeight() / data.getWidth());
                layoutParams.width = itemWidth;
                layoutParams.height = itemHeight;
                JCVideoPlayerStandard videoPlayer = (JCVideoPlayerStandard) holder.getView(R.id.videoplayer);
                videoPlayer.setUp(data.getVideoPlayUrl()
                        , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, data.getDescribe());
                videoPlayer.thumbImageView.setBackgroundColor(CommonHelper.randomColor());
            }
        };

        HFModule hfModule = new HFModule(getContext(), HFModule.NO_RES, R.layout.common_footer_load_more, getRgv().getRecyclerView());
        mAdapter.addHFModule(hfModule);
//        mAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
//            @Override
//            public void onItemClick(int pos, RvViewHolder holder) {
//                AlbumDetailActivity.loadActivity4DetailShow(getActivity(), datas.get(pos));
//            }
//        });
        mAdapter.addLoadMoreModule(new LoadMoreModule(mPreLoadNum, new OnLoadMoreListener() {
            @Override
            public void onLoadMore(LoadMoreModule mLoadMoreModule) {
                justQuery();
            }
        }));
    }
}
