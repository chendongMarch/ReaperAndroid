package com.march.reaper.iview.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.march.lib_base.common.Toaster;
import com.march.reaper.R;
import com.march.reaper.base.activity.MultiFragmentActivity;
import com.march.lib_base.presenter.BasePresenter;
import com.march.reaper.iview.fragment.HomeBeautyFragment;
import com.march.reaper.iview.fragment.HomeFunnyFragment;
import com.march.reaper.iview.fragment.HomeMineFragment;
import com.march.reaper.iview.fragment.HomeVideoFunFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * 主页分为四个fragment ,第一个fragment是viewpager(包含多个fragment)
 */
public class HomePageActivity extends MultiFragmentActivity {

    @Bind({R.id.home_recommend, R.id.home_album, R.id.home_search, R.id.home_mine})
    List<TextView> mBotTabsTv;

    @Override
    protected int getLayoutId() {
        return R.layout.homepage_activity;
    }

    @Override
    protected boolean whenShowSameFragment(int showItem) {
        return false;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.home_container;
    }

    @Override
    protected Fragment makeFragment(int showItem) {
        Fragment fragment = null;
        switch (showItem) {
            case 0:
                fragment = HomeVideoFunFragment.newInst();
                break;
            case 1:
                fragment = HomeFunnyFragment.newInst();
                break;
            case 2:
                fragment = HomeBeautyFragment.newInst();
                break;
            case 3:
                fragment = HomeMineFragment.newInst();
                break;
        }
        return fragment;
    }

    @Override
    protected boolean whenShowNotSameFragment(int showItem) {
        JCVideoPlayer.releaseAllVideos();
        return super.whenShowNotSameFragment(showItem);
    }

    @Override
    protected void syncSelectState(int selectImage) {
        for (int i = 0; i < mBotTabsTv.size(); i++) {
            mBotTabsTv.get(i).setSelected(selectImage == i);
        }
    }

    @OnClick({R.id.home_recommend, R.id.home_album, R.id.home_search, R.id.home_mine})
    public void click(View v) {
        int tag = Integer.parseInt(v.getTag().toString());
        showFragment(tag);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    protected String[] getPermission2Check() {
        return new String[0];
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    private long lastTryBackTime = 0;

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastTryBackTime < 1000) {
            super.onBackPressed();
        } else {
            lastTryBackTime = currentTimeMillis;
            Toaster.get().show(mContext, "再按一下退出");
        }
    }
}
