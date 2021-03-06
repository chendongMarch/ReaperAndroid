package com.march.reaper.base.mvp.presenter;

import android.widget.ImageView;

import com.march.lib.adapter.core.AbsAdapter;
import com.march.lib.adapter.core.BaseViewHolder;
import com.march.lib.core.common.DimensionHelper;
import com.march.lib.core.common.Logger;
import com.march.lib.core.mvp.presenter.BasePresenter;
//import com.march.lib.core.mvp.view.BaseView;
import com.march.lib.core.mvp.view.BaseView;
import com.march.reaper.common.Constant;
import com.march.reaper.helper.ImageHelper;
import com.march.reaper.widget.RecyclerGroupView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by march on 16/7/11.
 * 分页加载处理
 */
public abstract class BasePageLoadPresenter<V extends BaseView, D, A extends AbsAdapter<D>> extends BasePresenter<V> {
    protected A mAdapter;
    protected static final int mPreLoadNum = Constant.PRE_LOAD_NUM;
    protected int offset = 0, limit = Constant.ONECE_QUERY_DATA_NUM;
    protected boolean isLoadEnd = true;
    protected List<D> datas;
    protected int mWidth;


    protected void loadImg(BaseViewHolder holder, int res, String url) {
        ImageView imageView = (ImageView) holder.getView(res);
        ImageHelper.loadImg(getContext(), url.replaceAll("-\\d+x\\d+",""), imageView);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        mWidth = DimensionHelper.getScreenWidth(getContext());
        datas = new ArrayList<>();
        getRgv().setOnRefreshBeginListener(new RecyclerGroupView.OnRefreshBeginListener() {
            @Override
            public void onRefreshBegin() {
                reJustQuery();
            }
        });
    }


    public void reJustQuery() {
        datas.clear();
        offset = 0;
        justQuery();
    }


    /**
     * 处理查询后的数据
     *
     * @param list 获取到的数据
     */
    protected void handleDatasAfterQueryReady(List<D> list) {
        getRgv().setEnabled(false);
        if (list.size() <= 0) {
            offset = -1;
            Logger.e("没有数据了");
            if (mAdapter != null) {
                mAdapter.getHFModule().setFooterEnable(false);
            }
        } else {
            int preDataCount = this.datas.size() + 1;
            datas.addAll(list);
            if (mAdapter == null) {
                createRvAdapter();
                setAdapter4RecyclerView(mAdapter);
            } else {
                if (offset == 0)
                    mAdapter.notifyDataSetChanged();
                else
                    mAdapter.notifyItemRangeInserted(preDataCount, this.datas.size() - preDataCount - 1);
            }
            //查询成功,offset增加
            if (list.size() < limit) {
                offset = -1;
                Logger.e("数据不足");
                if (mAdapter != null) {
                    mAdapter.getHFModule().setFooterEnable(false);
                }
            } else {
                offset += limit;
            }
        }
        if (mAdapter != null)
            mAdapter.getLoadMoreModule().finishLoad();
        completeRefresh();
        isLoadEnd = true;
        getRgv().setEnabled(true);

    }

    protected abstract RecyclerGroupView getRgv();

    protected void completeRefresh() {
        getRgv().refreshComplete();
    }

    protected void setAdapter4RecyclerView(AbsAdapter adapter) {
        getRgv().getRecyclerView().setAdapter(adapter);
    }


    /**
     * 请求
     */
    public void justQuery() {
        if (offset == 0)
            getRgv().autoRefresh();
    }

    protected boolean checkCanQuery() {
        //如果没有数据,截断
        if (offset == -1)
            return false;
        //如果上次请求未完成,截断
        if (!isLoadEnd)
            return false;
        isLoadEnd = false;
        return true;
    }


    protected abstract void queryDbDatas();

    protected abstract void queryNetDatas();

    protected abstract void createRvAdapter();
}
