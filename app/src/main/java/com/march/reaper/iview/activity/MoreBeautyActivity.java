package com.march.reaper.iview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.march.lib.core.fragment.BaseFragment;
import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.lib.core.presenter.BasePresenter;
import com.march.reaper.common.Constant;
import com.march.reaper.iview.adapter.MyFragmentPagerAdapter;
import com.march.reaper.iview.fragment.BeautyAlbumFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.activity
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */
public class MoreBeautyActivity extends BaseReaperActivity {

    public static void startMoreBeauty(Activity activity) {
        activity.startActivity(new Intent(activity, MoreBeautyActivity.class));
    }

    @Bind(R.id.tably)
    TabLayout mTabLy;
    @Bind(R.id.viewpager)
    ViewPager mTypeAlbumVp;
    private List<BaseFragment> mFragments;


    @Override
    public void onInitViews(View view, Bundle save) {
        super.onInitViews(view, save);
        mTypeAlbumVp.setOffscreenPageLimit(7);
        mTypeAlbumVp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments));
        for (int i = 0; i < Constant.mMenuItem.length; i++) {
            mTabLy.addTab(mTabLy.newTab().setText(Constant.mMenuItem[i]).setTag(i));
        }
    }


    @Override
    public void onInitDatas() {
        super.onInitDatas();
        mFragments = new ArrayList<>();
        for (int i = 0; i < Constant.mMenuItem.length; i++) {
            mFragments.add(BeautyAlbumFragment.newInst(Constant.ALBUM_RECOMMEND, Constant.mRecommendType[i]));
        }
    }

    private int prePos = 0;

    @Override
    public void onInitEvents() {
        super.onInitEvents();
        mTabLy.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (Math.abs(prePos - position) == 1) {
                    mTypeAlbumVp.setCurrentItem(position, true);
                } else {
                    mTypeAlbumVp.setCurrentItem(position, false);
                }
                prePos = position;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTypeAlbumVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tabAt = mTabLy.getTabAt(position);
                if (tabAt != null) {
                    tabAt.select();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.tv_back})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                animFinish();
                break;
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.beauty_more_activity;
    }
}
