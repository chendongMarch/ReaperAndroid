package com.march.reaper.base.activity;

import android.support.annotation.NonNull;
import android.view.View;

import com.march.reaper.helper.PermissionHelper;
import com.march.reaper.widget.TitleBarView;

import java.util.Map;

/**
 * com.march.baselib.ui.activity
 * CommonLib
 * Created by chendong on 16/8/13.
 * Copyright © 2016年 chendong. All rights reserved.
 * Desc :
 */

/**
 * Project  : CommonLib
 * Package  : com.march.baselib
 * CreateAt : 16/8/15
 * Describe :
 *
 * @author chendong
 */
abstract class AbsActivityWrap extends AbsActivity {

    /**
     * title bar
     */
    protected TitleBarView mTitleBarView;

    @Override
    protected View getLayoutView() {
        if (isInitTitle()) {
            mTitleBarView = new TitleBarView(mActivity);
            getLayoutInflater().inflate(getLayoutId(), mTitleBarView, true);
        }
        return mTitleBarView;
    }

    /**
     * 监测权限
     */
    @Override
    protected boolean checkPermission() {
        return PermissionHelper.checkPermission(mActivity, getPermission2Check());
    }


    //监测权限返回结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionHelper.OnPermissionHandleOverListener() {
            @Override
            public void onHandleOver(boolean isOkExactly, Map<String, Integer> result) {
                //权限ok或者子类要求直接执行
                if (isOkExactly || handlePermissionResult(result))
                    invokeCommonMethod(mSaveBundle);
            }
        });
    }

    protected abstract String[] getPermission2Check();

    protected boolean handlePermissionResult(Map<String, Integer> resultNotOk) {
        return true;
    }

    protected abstract boolean isInitTitle();
}
