package com.march.reaper.mvp.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.march.reaper.R;
import com.march.reaper.mvp.ui.BaseFragment;
import com.march.reaper.mvp.ui.activity.OffLineDataActivity;

import butterknife.OnClick;

public class MineFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mSelfName = MineFragment.class.getSimpleName();
    }

    @OnClick(R.id.mine_offline)
    public void clickBtn(View v) {
        startActivity(new Intent(getActivity(), OffLineDataActivity.class));
    }
}
