package com.march.reaper.ipresenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.march.reaper.imodel.bean.VideoFun;
import com.march.lib.adapter.common.OnLoadMoreListener;
import com.march.lib.adapter.core.BaseViewHolder;
import com.march.lib.adapter.core.SimpleRvAdapter;
import com.march.lib.adapter.module.HFModule;
import com.march.lib.adapter.module.LoadMoreModule;
import com.march.lib.core.common.DimensionHelper;
import com.march.reaper.R;
import com.march.reaper.base.mvp.presenter.BasePageLoadPresenter;
import com.march.reaper.base.mvp.view.BaseRgvView;
import com.march.reaper.common.Constant;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.helper.CommonHelper;
import com.march.reaper.imodel.VideoFUnResponse;
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
public class VideoFunPresenter extends BasePageLoadPresenter<VideoFunPresenter.VideoFunView, VideoFun,SimpleRvAdapter<VideoFun>> {


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
        RequestCallback<VideoFUnResponse> requestCallback = new RequestCallback<VideoFUnResponse>() {
            @Override
            public void onSucceed(VideoFUnResponse rst) {
                List<VideoFun> data = rst.getData();
                handleDatasAfterQueryReady(data);
            }

            @Override
            public void onError(Throwable e) {
                if (mAdapter != null)
                    mAdapter.getLoadMoreModule().finishLoad();
                getRgv().refreshComplete();
                isLoadEnd = true;
            }
        };
        VideoFun.getVideoFunData(offset, limit, requestCallback);
    }

    @Override
    protected void createRvAdapter() {
        mAdapter = new SimpleRvAdapter<VideoFun>(getContext(), datas, R.layout.video_item_show) {
            @Override
            public void onBindView(BaseViewHolder holder, VideoFun data, int pos, int type) {
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
        mAdapter.addLoadMoreModule(new LoadMoreModule(mPreLoadNum, new OnLoadMoreListener() {
            @Override
            public void onLoadMore(LoadMoreModule mLoadMoreModule) {
                justQuery();
            }
        }));
    }
}
