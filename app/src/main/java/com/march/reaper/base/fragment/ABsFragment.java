package com.march.reaper.base.fragment;

import android.content.Context;
import android.content.Intent;
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
 * fragment
 */
public abstract class ABsFragment extends Fragment implements ILife {

    protected String mSelfName;
    protected Context mContext;


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
        ButterKnife.bind(this, view);
        onInitViews(view, savedInstanceState);
        onInitEvents();
        onStartWorks();
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
