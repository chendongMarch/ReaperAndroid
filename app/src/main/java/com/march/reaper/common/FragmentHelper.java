package com.march.reaper.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.march.lib.core.common.Logger;

/**
 * Project  : CommonLib
 * Package  : com.march.lib.core.common
 * CreateAt : 2016/11/5
 * Describe : 实现Fragment切换
 *
 * @author chendong
 */
public class FragmentHelper {

    interface FragmentOperator {
        /**
         * 获取放置fragment的控件id
         *
         * @return id
         */
        int getFragmentContainerId();

        /**
         * 构建fragment
         *
         * @param showItem 将要展示的fragment pos
         * @return fragment
         */
        Fragment makeFragment(int showItem);

        /**
         * 进行转换之前做操作,动画之类的
         *
         * @return FragmentTransaction
         */
        void beginTransaction(FragmentTransaction transaction);

        /**
         * 同步选中之后的显示状态
         *
         * @param selectImage 被选中的item
         */
        void syncSelectState(int selectImage);

        /**
         * 当点击显示同一个
         *
         * @param showItem 显示的item
         * @return 返回false表示忽略此次点击的切换
         */
        boolean whenShowSameFragment(int showItem);

        /**
         * 当点击显示的不是同一个
         *
         * @param showItem 显示的item
         * @return 返回false表示忽略此次点击的切换
         */
        boolean whenShowNotSameFragment(int showItem);
    }

    public static abstract class SimpleFragmentOperator implements FragmentOperator {
        @Override
        public boolean whenShowNotSameFragment(int showItem) {
            return true;
        }

        @Override
        public boolean whenShowSameFragment(int showItem) {
            return false;
        }

        @Override
        public void syncSelectState(int selectImage) {

        }

        @Override
        public void beginTransaction(FragmentTransaction transaction) {

        }
    }

    private static final String FRAGMENT_ATG = "FragmentHelper";
    private static final String ITEM_HIDE = "mHideItem";
    private static final String ITEM_SHOW = "mShowItem";

    private FragmentOperator operator;
    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;
    private int mShowItem, mHideItem;
    private int mExactlyItem = 0;


    public void restoreFragmentHelper(Bundle save) {
        if (save != null) {
            mHideItem = save.getInt(ITEM_HIDE, 0);
            mShowItem = save.getInt(ITEM_SHOW, 0);
        }
        performSelectItem(mHideItem, mShowItem, true);
    }


    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ITEM_HIDE, mHideItem);
        outState.putInt(ITEM_SHOW, mShowItem);
    }


    public FragmentHelper(FragmentManager mFragmentManager, FragmentOperator operator) {
        this.mFragmentManager = mFragmentManager;
        this.mFragmentManager = mFragmentManager;
        this.operator = operator;
    }


    /**
     * 显示某个fragment
     *
     * @param showItem 显示的item
     */
    public void showFragment(int showItem) {
        showFragment(showItem, false);
    }


    /**
     * 选中某一个fragment，处理重复点击
     *
     * @param showItem   显示的item
     * @param isOnCreate 是否是第一次创
     */
    private void showFragment(int showItem, boolean isOnCreate) {
        if (showItem == mShowItem) {
            if (operator.whenShowSameFragment(showItem)) {
                performSelectItem(mExactlyItem, showItem, isOnCreate);
                mExactlyItem = showItem;
            }
        } else {
            performSelectItem(mExactlyItem, showItem, isOnCreate);
            mExactlyItem = showItem;
        }
    }

    /**
     * 获取当前处于活动状态的fragment'
     *
     * @return fragment
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
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
        String currentTag = getFragmentTag(hideItem);
        // 隐藏当前的的fragment
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        operator.beginTransaction(transaction);

        // 第一次创建，一个都没有，不需要隐藏，直接显示
        if (mFragmentManager.getFragments() == null) {
            mShowItem = showItem;
            mExactlyItem = showItem;
            mCurrentFragment = operator.makeFragment(showItem);
            transaction.add(operator.getFragmentContainerId(), mCurrentFragment, getFragmentTag(showItem))
                    .show(mCurrentFragment);
        } else {
            // 优化，如果被杀后再进来，全部的fragment都会被呈现显示状态，所以都隐藏一遍
            if (isOnCreate && mFragmentManager.getFragments() != null) {
                for (Fragment fragment : mFragmentManager.getFragments()) {
                    transaction.hide(fragment);
                }
            } else {
                // 正常按钮点击进入，隐藏上一个即可
                Fragment lastFragment = mFragmentManager.findFragmentByTag(currentTag);
                if (lastFragment != null) {
                    transaction.hide(lastFragment);
                }
            }


            // 获得将要显示页的tag
            String toTag = getFragmentTag(showItem);
            // find要显示的Fragment
            mCurrentFragment = mFragmentManager.findFragmentByTag(toTag);
            if (mCurrentFragment != null) {
                // 已经存在则显示
                transaction.show(mCurrentFragment);
            } else {
                // 不存在则添加新的fragment
                mCurrentFragment = operator.makeFragment(showItem);
                if (mCurrentFragment != null) {
                    transaction.add(operator.getFragmentContainerId(), mCurrentFragment, toTag);
                }
            }
        }
        // 同步状态
        operator.syncSelectState(showItem);
        // 保存当前显示fragment的item
        mHideItem = hideItem;
        mShowItem = showItem;
        transaction.commitAllowingStateLoss();
    }


    private String getFragmentTag(int item) {
        return FRAGMENT_ATG + item;
    }
}
