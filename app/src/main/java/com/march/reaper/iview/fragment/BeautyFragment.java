package com.march.reaper.iview.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.march.reaper.R;
import com.march.reaper.base.fragment.AbsFragment;
import com.march.reaper.base.fragment.BaseFragment;
import com.march.reaper.iview.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.fragment
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public class BeautyFragment extends BaseFragment {

    @Bind(R.id.tably)
    TabLayout mTabLy;
    @Bind(R.id.viewpager)
    ViewPager mBeautyVp;
    private List<AbsFragment> mFragments;

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.beauty_fragment;
    }

    public static BeautyFragment newInst() {
        return new BeautyFragment();
    }

    @Override
    protected void onInitDatas() {
        super.onInitDatas();
        mFragments = new ArrayList<>();
        mFragments.add(BeautyRecommendFragment.newInst());
        mFragments.add(BeautyWholeFragment.newInst());
    }

    @Override
    protected void onInitViews(View view, Bundle save) {
        super.onInitViews(view, save);
        TabLayout.Tab recommendTab = mTabLy.newTab().setText("推荐");
        mTabLy.addTab(recommendTab, true);
        TabLayout.Tab wholeTab = mTabLy.newTab().setText("全部");
        mTabLy.addTab(wholeTab, false);
        mBeautyVp.setAdapter(
                new MyFragmentPagerAdapter(
                        getChildFragmentManager(), mFragments));
    }

    @Override
    protected void onInitEvents() {
        super.onInitEvents();
        mTabLy.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBeautyVp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mBeautyVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tabAt = mTabLy.getTabAt(position);
                if (tabAt != null)
                    tabAt.select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
