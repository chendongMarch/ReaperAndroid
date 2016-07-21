package com.march.reaper.mvp.presenter;

import android.content.Intent;

import com.march.reaper.mvp.ui.BaseView;
import com.march.reaper.mvp.ui.RootActivity;

/**
 * com.march.reaper.mvp.presenter
 * Created by chendong on 16/7/19.
 * desc : 针对activity的presenter基类
 */
public abstract class BaseActivityPresenter<V extends BaseView> extends WithViewTypePresenter<V> {

    public BaseActivityPresenter(RootActivity mContext) {
        super(mContext);
    }

    protected abstract void setIntent(Intent intent);
}
