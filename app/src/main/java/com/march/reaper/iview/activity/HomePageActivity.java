package com.march.reaper.iview.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.march.lib.core.common.Logger;
import com.march.lib.core.common.Toaster;
import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.common.FragmentHelper;
import com.march.reaper.iview.fragment.HomeBeautyFragment;
import com.march.reaper.iview.fragment.HomeFunnyFragment;
import com.march.reaper.iview.fragment.HomeMineFragment;
import com.march.reaper.iview.fragment.HomeVideoFunFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

//import com.march.lib.core.common.FragmentHelper;

/**
 * 主页分为四个fragment ,第一个fragment是viewpager(包含多个fragment)
 */
public class HomePageActivity extends BaseReaperActivity {

    @Bind({R.id.home_recommend, R.id.home_album, R.id.home_search, R.id.home_mine})
    List<TextView> mBotTabsTv;

    @Override
    protected int getLayoutId() {
        return R.layout.homepage_activity;
    }

    private FragmentHelper fragmentHelper;

    private FragmentHelper.SimpleFragmentOperator operator = new FragmentHelper.SimpleFragmentOperator() {
        @Override
        public int getFragmentContainerId() {
            return R.id.home_container;
        }

        @Override
        public Fragment makeFragment(int showItem) {
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
        public void beginTransaction(FragmentTransaction transaction) {
            super.beginTransaction(transaction);
        }

        @Override
        public void syncSelectState(int selectImage) {
            for (int i = 0; i < mBotTabsTv.size(); i++) {
                mBotTabsTv.get(i).setSelected(selectImage == i);
            }
        }

        @Override
        public boolean whenShowNotSameFragment(int showItem) {
            JCVideoPlayer.releaseAllVideos();
            return super.whenShowNotSameFragment(showItem);
        }
    };


    @Override
    public void onInitViews(View view, Bundle saveData) {
        super.onInitViews(view, saveData);
        fragmentHelper = new FragmentHelper(getSupportFragmentManager(), operator);
        fragmentHelper.showFragment(2);
    }

    @OnClick({R.id.home_recommend, R.id.home_album, R.id.home_search, R.id.home_mine})
    public void click(View v) {
        int tag = Integer.parseInt(v.getTag().toString());
        fragmentHelper.showFragment(tag);
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
