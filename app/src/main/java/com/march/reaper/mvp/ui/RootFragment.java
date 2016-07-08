package com.march.reaper.mvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march.bean.RecommendAlbumItem;
import com.march.reaper.R;
import com.march.reaper.utils.Lg;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by march on 16/7/1.
 *
 */
public abstract class RootFragment extends Fragment {
    protected abstract int getLayoutId();
    protected String mSelfName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this,view);
        initViews(view,savedInstanceState);
        initEvents();
        Lg.e(mSelfName + " onCreateView");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        Lg.e(mSelfName + " onCreate");
    }

    protected  void initDatas(){

    }

    protected  void initViews(View view,Bundle save){

    }

    protected  void initEvents(){

    }
}
