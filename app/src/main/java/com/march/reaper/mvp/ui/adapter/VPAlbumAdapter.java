package com.march.reaper.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.march.reaper.mvp.ui.BaseFragment;
import com.march.reaper.mvp.ui.fragment.AlbumQueryFragment;

import java.util.List;

/**
 * Created by march on 16/7/1.
 * 推荐页面ViewPager的适配器
 */
public class VPAlbumAdapter extends FragmentPagerAdapter{

    private List<BaseFragment> mFragments;

    public VPAlbumAdapter(FragmentManager fm) {
        super(fm);
    }

    public VPAlbumAdapter(FragmentManager fm, List<BaseFragment> mFragments) {
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
