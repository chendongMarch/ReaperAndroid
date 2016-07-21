package com.march.reaper.mvp.presenter;

import com.march.reaper.event.SucceedEntryAppEvent;
import com.march.reaper.mvp.ui.BaseView;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * com.march.reaper.mvp.presenter
 * Created by chendong on 16/7/19.
 * desc : 使用泛型约束View类型
 */
public abstract class WithViewTypePresenter<V extends BaseView> implements BasePresenter{

    //protected ApiService mApiService;
    protected V mView;
    protected RootActivity mContext;

    public WithViewTypePresenter(V mView, RootActivity mContext) {
        this.mView = mView;
        this.mContext = mContext;
        //mApiService = App.get().getNetComponent().getApiService();
    }

    public WithViewTypePresenter(RootActivity mContext) {
        this.mView = (V) mContext;
        this.mContext = mContext;
        //mApiService = App.get().getNetComponent().getApiService();
    }

    protected void authority() {
        SPUtils.get().putIsLogin(true);
        //通知app start关闭
        EventBus.getDefault().post(new SucceedEntryAppEvent(SucceedEntryAppEvent.EVENT_SUCCEED_ENTRY_APP));
    }
}
