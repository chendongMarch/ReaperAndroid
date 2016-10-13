package com.march.reaper.iview.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.march.reaper.R;
import com.march.reaper.ipresenter.impl.SearchPresenterImpl;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.base.fragment.BaseFragment;
import com.march.reaper.widget.RecyclerGroupView;

import butterknife.Bind;

/**
 * 搜索展示
 */
public class SearchFragment extends BaseFragment implements SearchPresenterImpl.SearchView {

    @Bind(R.id.rv_random_list)
    RecyclerGroupView mRandomRgv;
    private SearchPresenterImpl mPresenterImpl;

    @Override
    protected boolean isInitTitle() {
        return true;
    }

    @Override
    protected void destroyPresenter() {
        mPresenterImpl = null;
    }

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
        mTitleBarView.setText(null, "发现", null);
//        mRandomRgv.enableHeader();
        mRandomRgv.getFloatBtn().setImageResource(R.drawable.ic_random);
        mRandomRgv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mPresenterImpl = new SearchPresenterImpl(this, (BaseReaperActivity) getActivity());
        mPresenterImpl.setRgv(mRandomRgv);
        mPresenterImpl.justQuery();
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mRandomRgv.getFloatBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenterImpl.justQuery();
            }
        });
    }
}
