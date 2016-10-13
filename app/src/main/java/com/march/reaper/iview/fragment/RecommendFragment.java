package com.march.reaper.iview.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.march.reaper.R;
import com.march.reaper.common.Constant;
import com.march.reaper.iview.RootFragment;
import com.march.reaper.iview.adapter.VPAlbumAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by march on 16/7/1.
 * 推荐页面
 */
public class RecommendFragment extends RootFragment {

    @Bind(R.id.recommend_tably)
    TabLayout mTabLy;
    @Bind(R.id.recommend_viewpager)
    ViewPager mTypeAlbumVp;
    private List<RootFragment> mFragments;


    @Override
    protected void initViews(View view, Bundle save) {
        super.initViews(view, save);
        mTypeAlbumVp.setOffscreenPageLimit(8);
        mTypeAlbumVp.setAdapter(new VPAlbumAdapter(getFragmentManager(), mFragments));
        for (int i = 0; i < Constant.mMenuItem.length; i++) {
            mTabLy.addTab(mTabLy.newTab().setText(Constant.mMenuItem[i]).setTag(i));
        }
    }


    @Override
    protected void initDatas() {
        super.initDatas();
        mFragments = new ArrayList<>();
        mSelfName = RecommendFragment.class.getSimpleName();
        for (int i = 0; i < Constant.mMenuItem.length; i++) {
            mFragments.add(AlbumQueryFragment.newInst(Constant.mMenuItem[i], Constant.mRecommendType[i]));
        }
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTabLy.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTypeAlbumVp.setCurrentItem((int) tab.getTag());
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

    @Override
    protected void destroyPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.recommend_fragment;
    }


}
