package com.march.reaper.base.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Project  : CommonLib
 * Package  : com.march.baselib
 * CreateAt : 16/8/15
 * Describe : 处理一个Activity包含多个Fragment的相关交互
 *
 * @author chendong
 */
public abstract class MultiFragmentActivity extends BaseReaperMVPActivity {

    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;
    private int mShowItem, mHideItem;
    private int mExactlyItem = 0;
    private static final String ITEM_HIDE = "mHideItem";
    private static final String ITEM_SHOW = "mShowItem";

    @Override
    protected void onInitViews(Bundle save) {
        super.onInitViews(save);
        mFragmentManager = getSupportFragmentManager();
        // 从savedInstanceState获取到保存的mCurrentItem
        if (save != null) {
            mHideItem = save.getInt(ITEM_HIDE, 0);
            mShowItem = save.getInt(ITEM_SHOW, 0);
        }
        performSelectItem(mHideItem, mShowItem, true);
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(ITEM_HIDE, mHideItem);
        outState.putInt(ITEM_SHOW, mShowItem);
    }

    /**
     * 隐藏当前显示的fragment,显示将要显示的fragment
     *
     * @param hideItem   需要隐藏的fragment
     * @param showItem   需要显示的fragment
     * @param isOnCreate 是否是第一次从OnCreate中启动,点击都是false
     */
    private void performSelectItem(int hideItem, int showItem, boolean isOnCreate) {
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
                transaction.add(getFragmentContainerId(), mCurrentFragment, toTag);
            }
        }
        // 同步状态
        syncSelectState(showItem);
        // 保存当前显示fragment的item
        mHideItem = hideItem;
        mShowItem = showItem;
        transaction.commitAllowingStateLoss();
    }

    /**
     * 选中某一个fragment
     *
     * @param showItem   显示的item
     * @param isOnCreate 是否是第一次创
     */
    protected void showFragment(int showItem, boolean isOnCreate) {
        if (showItem == mShowItem) {
            if (whenShowSameFragment(showItem)) {
                performSelectItem(mExactlyItem, showItem, isOnCreate);
                mExactlyItem = showItem;
            }
        } else {
            performSelectItem(mExactlyItem, showItem, isOnCreate);
            mExactlyItem = showItem;
        }
    }

    /**
     * 显示某个fragment
     *
     * @param showItem 显示的item
     */
    protected void showFragment(int showItem) {
        showFragment(showItem, false);
    }

    /**
     * 当点击显示同一个
     *
     * @param showItem 显示的item
     * @return 返回false表示忽略此次点击的切换
     */
    protected abstract boolean whenShowSameFragment(int showItem);

    /**
     * 获取当前处于活动状态的fragment'
     *
     * @return fragment
     */
    protected Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    /**
     * 获取放置fragment的控件id
     *
     * @return id
     */
    protected abstract int getFragmentContainerId();

    /**
     * 构建fragment
     *
     * @param showItem 将要展示的fragment pos
     * @return fragment
     */
    protected abstract Fragment makeFragment(int showItem);


    /**
     * 同步选中之后的显示状态
     *
     * @param selectImage 被选中的item
     */
    protected abstract void syncSelectState(int selectImage);

}