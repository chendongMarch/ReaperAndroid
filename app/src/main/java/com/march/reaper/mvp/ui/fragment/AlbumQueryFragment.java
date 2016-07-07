package com.march.reaper.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.march.bean.RecommendAlbumItem;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.reaper.R;
import com.march.reaper.common.Constant;
import com.march.reaper.common.DbHelper;
import com.march.reaper.mvp.presenter.impl.AlbumQuery4WholePresenterImpl;
import com.march.reaper.mvp.presenter.impl.AlbumQueryPresenterImpl;
import com.march.reaper.mvp.ui.BaseFragment;
import com.march.reaper.utils.DisplayUtils;
import com.march.reaper.utils.Lg;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by march on 16/7/1.
 * 专辑展示
 */
public class AlbumQueryFragment extends BaseFragment {

    @Bind(R.id.albumquery_recycler)
    RecyclerView mAlbumsRv;
    @Bind(R.id.albumquery_title)
    ViewGroup mTitleBar;
    private boolean isWholeAlbum;
    private String mTitle;
    private String mRecommendType;
    private AlbumQuery4WholePresenterImpl mAlbumQuery4WholePresenterImpl;


    public static AlbumQueryFragment newInst(String title, String type) {
        AlbumQueryFragment albumQueryFragment = new AlbumQueryFragment();
        Bundle args = new Bundle();
        args.putString(Constant.KEY_ALBUM_TITLE, title);
        args.putString(Constant.KEY_ALBUM_RECOMMEND_TYPE, type);

        albumQueryFragment.setArguments(args);
        return albumQueryFragment;
    }

    public static AlbumQueryFragment newInst() {
        AlbumQueryFragment albumQueryFragment = new AlbumQueryFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constant.KEY_IS_WHOLE_ALBUM, true);
        albumQueryFragment.setArguments(args);
        return albumQueryFragment;
    }


    @Override
    protected void initDatas() {
        super.initDatas();
        mTitle = getArguments().getString(Constant.KEY_ALBUM_TITLE);
        mRecommendType = getArguments().getString(Constant.KEY_ALBUM_RECOMMEND_TYPE);
        isWholeAlbum = getArguments().getBoolean(Constant.KEY_IS_WHOLE_ALBUM);
        mSelfName = AlbumQueryFragment.class.getSimpleName() + mTitle;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.albumquery_fragment;
    }

    @Override
    protected void initViews(View view, Bundle save) {
        super.initViews(view, save);
        if (isWholeAlbum) {
            mTitleBar.setVisibility(View.VISIBLE);
            mAlbumsRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mAlbumQuery4WholePresenterImpl = new AlbumQuery4WholePresenterImpl(getActivity(), mAlbumsRv);
            mAlbumQuery4WholePresenterImpl.queryDatas();
        } else {
            mAlbumsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            AlbumQueryPresenterImpl mAlbumQueryPresenterImpl =
                    new AlbumQueryPresenterImpl(getActivity(), mAlbumsRv, mRecommendType);
            mAlbumQueryPresenterImpl.queryDatas();
        }
    }

    @OnClick(R.id.albumquery_switchmode)
    public void clickBtn(View v) {
        mAlbumQuery4WholePresenterImpl.switchMode((TextView) v);
    }

}
