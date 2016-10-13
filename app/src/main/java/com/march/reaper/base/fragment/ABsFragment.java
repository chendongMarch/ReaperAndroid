package com.march.reaper.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by march on 16/7/1.
 * fragment
 */
public abstract class AbsFragment extends Fragment {

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
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitDatas();
    }

    protected abstract int getLayoutId();

    protected View getLayoutView() {
        return null;
    }

    protected void onInitDatas() {

    }

    protected void onInitViews(View view, Bundle save) {

    }

    protected void onInitEvents() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
