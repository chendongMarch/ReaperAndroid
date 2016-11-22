package com.march.reaper.iview.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.march.lib.core.fragment.BaseFragment;
import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.reaper.R;
import com.march.reaper.base.fragment.BaseReaperFragment;
import com.march.reaper.common.Constant;
import com.march.reaper.iview.activity.MoreBeautyActivity;
import com.march.reaper.iview.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.fragment
 * CreateAt : 2016/10/13
 * Describe : viewpager+两个fragment,懒加载
 *
 * @author chendong
 */

public class HomeBeautyFragment extends BaseReaperFragment {

    @Bind(R.id.tably)
    TabLayout mTabLy;
    @Bind(R.id.viewpager)
    ViewPager mBeautyVp;
    private List<BaseFragment> mFragments;

    @Override
    public boolean forceLoad() {
        return false;
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.beauty_fragment;
    }

    public static HomeBeautyFragment newInst() {
        return new HomeBeautyFragment();
    }

    @Override
    public void onInitDatas() {
        super.onInitDatas();
        mSelfName = "home beauty fragment";
        mFragments = new ArrayList<>();
        mFragments.add(BeautyAlbumFragment.newInst(Constant.ALBUM_RECOMMEND, Constant.TYPE_ALL_RECOMMEND_ALBUM));
        mFragments.add(BeautyAlbumFragment.newInst(Constant.ALBUM_WHOLE, null));
    }

    @Override
    public void onInitViews(View view, Bundle save) {
        super.onInitViews(view, save);
        TabLayout.Tab recommendTab = mTabLy.newTab().setText("推荐");
        mTabLy.addTab(recommendTab, true);
        TabLayout.Tab wholeTab = mTabLy.newTab().setText("全部");
        mTabLy.addTab(wholeTab, false);
        mBeautyVp.setOffscreenPageLimit(2);
        mBeautyVp.setAdapter(
                new MyFragmentPagerAdapter(
                        getFragmentManager(), mFragments));
    }

    @OnClick({R.id.tv_more_album})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.tv_more_album:
                MoreBeautyActivity.startMoreBeauty(getActivity());
                break;
        }
    }

    @Override
    public void onInitEvents() {
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

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
