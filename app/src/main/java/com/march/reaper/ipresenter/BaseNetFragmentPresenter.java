package com.march.reaper.ipresenter;


import com.march.reaper.iview.BaseView;
import com.march.reaper.iview.RootActivity;

/**
 * Created by march on 16/7/2.
 * fragment presenter基类
 */
public abstract class BaseNetFragmentPresenter<V extends BaseView,D> extends NetLoadListPresenter<V,D> {
    public abstract void switchMode();
    public BaseNetFragmentPresenter(V mView, RootActivity mContext) {
        super(mView, mContext);
    }
}
