package com.march.reaper.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.march.reaper.mvp.ui.RootFragment;

import java.util.List;

/**
 * Created by march on 16/7/1.
 * 推荐页面ViewPager的适配器
 */
public class VPAlbumAdapter extends FragmentPagerAdapter{

    private List<RootFragment> mFragments;

    public VPAlbumAdapter(FragmentManager fm) {
        super(fm);
    }

    public VPAlbumAdapter(FragmentManager fm, List<RootFragment> mFragments) {
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
