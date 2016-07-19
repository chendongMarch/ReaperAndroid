package com.march.reaper.mvp.presenter;

import com.march.reaper.mvp.ui.BaseView;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.utils.SPUtils;

/**
 * com.march.reaper.mvp.presenter
 * Created by chendong on 16/7/19.
 * desc :presenter真正功能上的基类
 */
public abstract class EasyPresenter<V extends BaseView> {

    //protected ApiService mApiService;
    protected V mView;
    protected RootActivity mContext;

    public EasyPresenter(V mView, RootActivity mContext) {
        this.mView = mView;
        this.mContext = mContext;
        //mApiService = App.get().getNetComponent().getApiService();
    }

    protected void authority() {
        SPUtils.get().putIsLogin(true);
    }
}
