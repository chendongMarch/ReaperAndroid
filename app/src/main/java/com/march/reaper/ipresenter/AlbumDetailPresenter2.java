package com.march.reaper.ipresenter;

import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.march.lib.adapter.common.OnLoadMoreListener;
import com.march.lib.adapter.common.SimpleItemListener;
import com.march.lib.adapter.core.BaseViewHolder;
import com.march.lib.adapter.core.SimpleRvAdapter;
import com.march.lib.adapter.module.HFModule;
import com.march.lib.adapter.module.LoadMoreModule;
import com.march.lib.core.common.Toaster;
import com.march.reaper.R;
import com.march.reaper.base.mvp.presenter.BasePageLoadPresenter;
import com.march.reaper.base.mvp.view.BaseRgvView;
import com.march.reaper.common.Constant;
import com.march.reaper.common.DbHelper;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.helper.CommonHelper;
import com.march.reaper.helper.ImageHelper;
import com.march.reaper.imodel.AlbumDetailResponse;
import com.march.reaper.imodel.bean.AlbumDetail;
import com.march.reaper.imodel.bean.AlbumItemCollection;
import com.march.reaper.imodel.bean.BeautyAlbum;
import com.march.reaper.iview.activity.ScanImgListActivity;
import com.march.reaper.widget.RecyclerGroupView;
import com.march.reaper.widget.SpaceDecoration;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by march on 16/7/2.
 * 详情
 */
public class AlbumDetailPresenter2
        extends BasePageLoadPresenter<AlbumDetailPresenter2.AlbumDetailView, AlbumDetail, SimpleRvAdapter<AlbumDetail>> {

    private boolean isBig = false;
    private BeautyAlbum mAlbumData;
    private boolean isCollection;
    private AlbumItemCollection mCol;

    private RecyclerView.ItemDecoration mGridDecoration;

    public interface AlbumDetailView extends BaseRgvView {
        void setModeTvText(String txt);

        void loadHeaderZoomView(String url);
    }


    @Override
    public void attachView(AlbumDetailView view) {
        super.attachView(view);
        Bundle intent = mView.getData();
        mGridDecoration = SpaceDecoration.gridWithHeader(10, 2, true);

        mView.getRgv().setLayoutManager(new GridLayoutManager(mView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        mView.getRgv().getRecyclerView().addItemDecoration(mGridDecoration);
        mAlbumData = intent.getParcelable(Constant.KEY_ALBUM_DETAIL_SHOW);
        mCol = new AlbumItemCollection(mAlbumData);
        isCollection = DbHelper.get().isAlbumCollection(mCol);
    }

    @Override
    protected RecyclerGroupView getRgv() {
        return mView.getRgv();
    }

    @Override
    public void justQuery() {
        super.justQuery();
        if (checkCanQuery())
            queryNetDatas();
    }


    @Override
    public void queryDbDatas() {
        DbHelper.get().queryAlbumDetail(mAlbumData.getAlbum_link(), offset, limit, new DbHelper.OnQueryReadyListener<AlbumDetail>() {
            @Override
            public void queryReady(List<AlbumDetail> list) {
                handleDatasAfterQueryReady(list);
            }
        });
    }


    //从网络获取数据
    @Override
    protected void queryNetDatas() {

        RequestCallback<AlbumDetailResponse> callback = new RequestCallback<AlbumDetailResponse>() {
            @Override
            public void onSucceed(AlbumDetailResponse data) {
                List<AlbumDetail> list = data.getData();
                handleDatasAfterQueryReady(list);
            }

            @Override
            public void onError(Throwable e) {
                if (mAdapter != null)
                    mAdapter.getLoadMoreModule().finishLoad();
                completeRefresh();
                isLoadEnd = true;
            }
        };
        AlbumDetail.getAlbumDetail(offset, limit, mAlbumData.getAlbum_link(), callback);
    }


    //构建adapter
    @Override
    protected void createRvAdapter() {
        mAdapter = new SimpleRvAdapter<AlbumDetail>(getContext(), datas, R.layout.detail_item_show_grid) {
            @Override
            public void onBindView(BaseViewHolder holder, AlbumDetail data, int pos, int type) {
                View iv = holder.getView(R.id.detail_item_show_iv);
                ImageHelper.loadImg(getContext(), data.getPhoto_src(), (ImageView) iv);
                View bgView = holder.getView(R.id.detail_show_bg);
                bgView.setBackgroundColor(CommonHelper.randomColor());

                float rate;
                rate = 1024 * 1.0f / 683;
                if (isBig) {
                    iv.getLayoutParams().height = (int) (mWidth * rate);
                } else {
                    iv.getLayoutParams().height = (int) (mWidth * rate * 0.5f);
                }
            }

            @Override
            public void onBindFooter(BaseViewHolder footer) {
                super.onBindFooter(footer);
                footer.setClickLis(R.id.footer_loadmore, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        justQuery();
                    }
                });
            }

            @Override
            public void onBindHeader(BaseViewHolder header) {
                super.onBindHeader(header);
                final ImageView mIsColIv = (ImageView) header.getView(R.id.iv_is_collection);
                //喜爱
                mIsColIv.setImageResource(isCollection ? R.drawable.ic_collection : R.drawable.ic_not_collection);
                mIsColIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isCollection) {
                            DbHelper.get().removeAlbumCollection(mCol);
                            mIsColIv.setImageResource(R.drawable.ic_not_collection);
                        } else {
                            DbHelper.get().addAlbumCollection(mCol);
                            mIsColIv.setImageResource(R.drawable.ic_collection);
                        }
                        isCollection = !isCollection;
                    }
                });
                //标签
                final TagFlowLayout mKeyWdsFlow = (TagFlowLayout) header.getView(R.id.head_keywds_flow);
                TextView mDescTv = (TextView) header.getView(R.id.head_desc_tv);
                mDescTv.setText(mAlbumData.getAlbum_desc());
                String keyWdsStr = mAlbumData.getKey_words();
                if (keyWdsStr == null) {
                    mKeyWdsFlow.setVisibility(View.GONE);
                } else {
                    String[] keyWds = keyWdsStr.split(", ");
                    TagAdapter<String> adapter = new TagAdapter<String>(keyWds) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            TextView tv = (TextView) getActivity().getLayoutInflater().inflate(R.layout.detail_item_keywds,
                                    mKeyWdsFlow, false);
                            tv.setText(s);
                            return tv;
                        }
                    };
                    mKeyWdsFlow.setAdapter(adapter);
                }
            }
        };
        HFModule hfModule = new HFModule(getContext(), R.layout.detail_head_list, R.layout.common_footer_load_more, getRgv().getRecyclerView());
        mAdapter.addHFModule(hfModule);
        mAdapter.addLoadMoreModule(new LoadMoreModule(mPreLoadNum, new OnLoadMoreListener() {
            @Override
            public void onLoadMore(LoadMoreModule mLoadMoreModule) {
                justQuery();
            }
        }));

        mAdapter.setItemListener(new SimpleItemListener<AlbumDetail>() {
            @Override
            public void onClick(int pos, BaseViewHolder holder, AlbumDetail data) {
                ScanImgListActivity.loadActivity(getActivity(), datas, pos);
            }

            @Override
            public void onDoubleClick(int pos, BaseViewHolder holder, AlbumDetail data) {
                super.onDoubleClick(pos, holder, data);
                Toaster.get().show(mView.getContext(),"已经添加到收藏");
            }
        });
    }

    public void switchMode() {
        isBig = !isBig;
        RecyclerView.LayoutManager layoutManager;
        if (isBig) {
            mView.setModeTvText("小图");
            mView.getRgv().getRecyclerView().removeItemDecoration(mGridDecoration);
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        } else {
            mView.setModeTvText("大图");
            mView.getRgv().getRecyclerView().addItemDecoration(mGridDecoration);
            layoutManager = new GridLayoutManager(mView.getContext(), 2, GridLayoutManager.VERTICAL, false);
        }
        getRgv().setLayoutManager(layoutManager);
        getRgv().setAdapter(mAdapter);
    }
}
