package com.march.reaper.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march.reaper.base.ILife;
import com.march.reaper.helper.Logger;

import butterknife.ButterKnife;

/**
 * Created by march on 16/7/1.
 * fragment
 */
public abstract class BaseAbsFragment extends Fragment implements ILife {

    protected String mSelfName;
    protected Context mContext;

    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;
    private boolean isCreateView;
    private boolean isLoadOver;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            Logger.e(mSelfName + " 可见 " + isVisible + "  " + isPrepared + "  " + isFirst);
            isVisible = true;
            if (isCreateView && !isLoadOver) {
                onStartWorks();
                isLoadOver = true;
            }
        } else {
            Logger.e(mSelfName + " 不可见 " + isVisible + "  " + isPrepared + "  " + isFirst);
            isVisible = false;
//            onInvisible();
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    protected abstract boolean isInitTitle();

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
        if (isVisible||forceLoad()) {
            onStartWorks();
            isLoadOver = true;
        }
        Logger.e(mSelfName + " create view");
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitIntent(getArguments());
        onInitDatas();
    }

    protected abstract int getLayoutId();

    protected View getLayoutView() {
        return null;
    }

    @Override
    public void onInitIntent(Bundle intent) {

    }

    public boolean forceLoad() {
        return false;
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
