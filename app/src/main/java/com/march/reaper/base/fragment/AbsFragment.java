package com.march.reaper.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march.reaper.base.ILife;

import butterknife.ButterKnife;

/**
 * Created by march on 16/7/1.
 * fragment基类，主要负责处理
 * 0. 分离逻辑，不对外开放
 * 1. 加载周期
 * 2. fragment懒加载相关逻辑
 */
abstract class AbsFragment extends Fragment implements ILife {

    protected String mSelfName;
    protected Context mContext;
    protected boolean isVisible;
    private boolean isCreateView;
    private boolean isLoadOver;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            if (isCreateView && !isLoadOver) {
                onStartWorks();
                isLoadOver = true;
            }
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitIntent(getArguments());
        onInitDatas();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if (getLayoutView() == null) {
            view = inflater.inflate(getLayoutId(), container, false);
        } else {
            view = getLayoutView();
        }
        isCreateView = true;
        ButterKnife.bind(this, view);
        onInitViews(view, savedInstanceState);
        onInitEvents();
        if (isVisible || forceLoad()) {
            onStartWorks();
            isLoadOver = true;
        }
        return view;
    }

    //默认不进行懒加载，当viewpager+fragment时需要懒加载
    public boolean forceLoad() {
        return true;
    }


    protected abstract int getLayoutId();


    protected View getLayoutView() {
        return null;
    }

    @Override
    public void onInitIntent(Bundle intent) {

    }

    @Override
    public void onInitDatas() {

    }


    @Override
    public void onInitViews(View view, Bundle saveData) {

    }

    @Override
    public void onInitEvents() {

    }

    @Override
    public void onStartWorks() {

    }
}
