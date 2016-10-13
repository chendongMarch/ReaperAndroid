package com.march.reaper.ipresenter;

import android.content.Intent;

import com.march.reaper.iview.BaseView;
import com.march.reaper.iview.RootActivity;

/**
 * com.march.reaper.mvp.presenter
 * Created by chendong on 16/7/19.
 * desc : 针对activity的presenter可以访问网络的基类 BaseNetActivityPresenter
 */
public abstract class BaseNetActivityPresenter<V extends BaseView,D> extends NetLoadListPresenter<V,D> {

    public BaseNetActivityPresenter(RootActivity mContext) {
        super(mContext);
    }

    public abstract void setIntent(Intent intent);

    public abstract void switchMode();
}
