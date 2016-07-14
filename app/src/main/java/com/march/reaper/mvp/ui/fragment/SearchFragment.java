package com.march.reaper.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.march.reaper.R;
import com.march.reaper.mvp.presenter.FragmentPresenter;
import com.march.reaper.mvp.presenter.impl.AlbumCollectionPresenterImpl;
import com.march.reaper.mvp.presenter.impl.SearchPresenterImpl;
import com.march.reaper.mvp.ui.TitleFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 搜索展示
 */
public class SearchFragment extends TitleFragment {

    @Bind(R.id.rv_random_list)
    RecyclerView mRandomRv;
    private FragmentPresenter mPresenterImpl;

    @Override
    protected int getLayoutId() {
        return R.layout.search_fragment;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mSelfName = SearchFragment.class.getSimpleName();
    }

    @Override
    protected void initViews(View view, Bundle save) {
        super.initViews(view, save);
        mTitleBar.setText(null, "发现", null);
        mPresenterImpl = new SearchPresenterImpl(getActivity(), mRandomRv);
        mPresenterImpl.justQuery();
    }

    @OnClick({R.id.fab_random})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.fab_random:
                mPresenterImpl.justQuery();
                break;
        }
    }
}
