package com.march.reaper.iview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.march.lib.core.fragment.BaseFragment;

import java.util.List;

/**
 * Created by march on 16/7/1.
 * 推荐页面ViewPager的适配器
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


}
