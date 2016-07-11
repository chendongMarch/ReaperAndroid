package com.march.reaper.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.march.reaper.R;
import com.march.reaper.common.Constant;
import com.march.reaper.common.TitleBarView;
import com.march.reaper.mvp.presenter.impl.AlbumQuery4WholePresenterImpl;
import com.march.reaper.mvp.presenter.impl.AlbumQueryPresenterImpl;
import com.march.reaper.mvp.ui.RootFragment;
import com.march.reaper.widget.RecyclerGroupView;


import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by march on 16/7/1.
 * 专辑展示
 */
public class AlbumQueryFragment extends RootFragment {

    @Bind(R.id.albumquery_recycler)
    RecyclerGroupView mAlbumsRgv;
    @Bind(R.id.titlebar_root)
    ViewGroup mTitleBarRoot;
    private boolean isWholeAlbum;
    private String mTitle;
    private String mRecommendType;
    private AlbumQuery4WholePresenterImpl mAlbumQuery4WholePresenterImpl;
    private TitleBarView mTitleBar;

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
        if (mRecommendType == null)
            mRecommendType = "all";
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
        mTitleBar = new TitleBarView(getActivity(), mTitleBarRoot);
        if (isWholeAlbum) {
            mTitleBar.setText(null, "专辑", "大图");
            mAlbumsRgv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mAlbumQuery4WholePresenterImpl = new AlbumQuery4WholePresenterImpl(mAlbumsRgv,getActivity());
            mAlbumQuery4WholePresenterImpl.justQuery();
        } else {
            mTitleBar.hide();
            mAlbumsRgv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            AlbumQueryPresenterImpl mAlbumQueryPresenterImpl =
                    new AlbumQueryPresenterImpl(getActivity(), mAlbumsRgv, mRecommendType);
            mAlbumQueryPresenterImpl.justQuery();
        }
    }

    @OnClick(R.id.tv_titlebar_right)
    public void clickBtn(View v) {
        mAlbumQuery4WholePresenterImpl.switchMode((TextView) v);
    }

}
