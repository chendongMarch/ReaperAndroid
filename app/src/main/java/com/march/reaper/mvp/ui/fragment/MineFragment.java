package com.march.reaper.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.reaper.R;
import com.march.reaper.common.Constant;
import com.march.reaper.mvp.ui.RootFragment;
import com.march.reaper.mvp.ui.activity.OffLineDataActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的页面
 */
public class MineFragment extends RootFragment {

    @Bind(R.id.rv_mine)
    RecyclerView mContentRv;

    @Override
    protected int getLayoutId() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mSelfName = MineFragment.class.getSimpleName();
    }

    @Override
    protected void initViews(View view, Bundle save) {
        super.initViews(view, save);
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        SimpleRvAdapter<String> mContentAdapter = new SimpleRvAdapter<String>(getActivity(), Constant.MINE_CONTENT_LIST, R.layout.mine_item_content) {
            @Override
            public void bindData4View(RvViewHolder holder, String data, int pos) {
                holder.setText(R.id.tv_mine_item_info, data);

            }
        };
        mContentRv.setAdapter(mContentAdapter);
    }

    @OnClick(R.id.mine_offline)
    public void clickBtn(View v) {
        startActivity(new Intent(getActivity(), OffLineDataActivity.class));
    }
}
