package com.march.reaper.iview.activity;

import android.support.v4.content.Loader;

import com.march.reaper.base.activity.BaseReaperMVPActivity;
import com.march.reaper.base.mvp.life.PresenterLoader;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.activity
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */
public class MoreBeautyActivity extends BaseReaperMVPActivity{

    @Override
    protected String[] getPermission2Check() {
        return new String[0];
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected PresenterLoader createPresenterLoader() {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }
}
