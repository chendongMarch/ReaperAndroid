package com.march.reaper.mvp.presenter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.march.bean.RecommendAlbumItem;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.reaper.common.Constant;
import com.march.reaper.utils.Lg;
import com.march.reaper.widget.RecyclerGroupView;

import java.util.List;

/**
 * Created by march on 16/7/11.
 * 网络请求列表显示
 */
public abstract class NetLoadListPresenter<T> {
    protected static final int mPreLoadNum = Constant.PRE_LOAD_NUM;
    protected int offset = 0, limit = Constant.ONECE_QUERY_DATA_NUM;
    protected boolean isLoadEnd = true;
    protected RecyclerGroupView mRecyclerGV;
    protected Activity mContext;
    protected List<T> ds;

    public NetLoadListPresenter(RecyclerGroupView mRecyclerGV, Activity mContext) {
        this.mRecyclerGV = mRecyclerGV;
        this.mContext = mContext;
        mRecyclerGV.setOnRefreshBeginListener(new RecyclerGroupView.OnRefreshBeginListener() {
            @Override
            public void onRefreshBegin() {
                clearDatas();
                offset = 0;
                justQuery();
            }
        });
    }

    public NetLoadListPresenter(Activity mContext) {
        this.mContext = mContext;
    }

    public void justQuery() {
        //如果没有数据,截断
        if (offset == -1)
            return;
        //如果上次请求未完成,截断
        if (!isLoadEnd)
            return;
        isLoadEnd = false;
        queryNetDatas();
    }

    protected void addAllData(List list) {
    }

    protected abstract void clearDatas();

    protected abstract void queryDbDatas();

    protected abstract void queryNetDatas();

    protected abstract void createRvAdapter();
}
