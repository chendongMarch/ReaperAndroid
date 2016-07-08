package com.march.reaper.mvp.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.march.reaper.RootActivity;

/**
 * Created by march on 16/7/1.
 * 切换多个fragment的activity
 */
public abstract class MultiFragmentActivity extends RootActivity {

    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;
    private int mShowItem, mHideItem;

    @Override
    protected abstract int getLayoutId();

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        // 从savedInstanceState获取到保存的mCurrentItem
        if (savedInstanceState != null) {
            mHideItem = savedInstanceState.getInt("mHideItem", 0);
            mShowItem = savedInstanceState.getInt("mShowItem", 0);
        }
        performSelectItem(mHideItem, mShowItem, true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("mHideItem", mHideItem);
        outState.putInt("mShowItem", mShowItem);
    }

    /**
     * 隐藏当前显示的fragment,显示将要显示的fragment
     *
     * @param hideItem   需要隐藏的fragment
     * @param showItem   需要显示的fragment
     * @param isOnCreate 是否是第一次从OnCreate中启动,点击都是flase
     */
    public void performSelectItem(int hideItem, int showItem, boolean isOnCreate) {
        // 获得将要显示页的tag
        String currentTag = "fragment" + hideItem;
        // 隐藏当前的的fragment
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // 如果被杀后再进来，全部的fragment都会被呈现显示状态，所以都隐藏一边
        if (isOnCreate && mFragmentManager.getFragments() != null) {
            for (Fragment fragment : mFragmentManager.getFragments()) {
                transaction.hide(fragment);
            }
        } else {
            // 正常按钮点击进入
            Fragment lastFragment = mFragmentManager.findFragmentByTag(currentTag);
            if (lastFragment != null) {
                transaction.hide(lastFragment);
            }
        }
        // 获得将要显示页的tag
        String toTag = "fragment" + showItem;
        // find要显示的Fragment
        mCurrentFragment = mFragmentManager.findFragmentByTag(toTag);
        if (mCurrentFragment != null) {
            // 已经存在则显示
            transaction.show(mCurrentFragment);
        } else {
            // 不存在则添加新的fragment
            mCurrentFragment = makeFragment(showItem);
            if (mCurrentFragment != null) {
                transaction.add(getFragmentId(), mCurrentFragment, toTag);
            }
        }
        // 选择image图片
        syncSelectState(showItem);
        // 保存当前显示fragment的item
        mHideItem = hideItem;
        mShowItem = showItem;
        transaction.commitAllowingStateLoss();
    }

    protected Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    protected abstract int getFragmentId();

    protected abstract Fragment makeFragment(int showItem);

    protected abstract void syncSelectState(int selectImage);

}
