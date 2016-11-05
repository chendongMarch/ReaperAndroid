package com.march.reaper.base.activity;

import android.widget.TextView;

import com.march.lib.core.activity.BaseMVPActivity;
import com.march.lib.core.presenter.BasePresenter;
import com.march.reaper.helper.SharePreferenceHelper;

import butterknife.ButterKnife;

/**
 * Created by march on 16/6/6.
 * activity基类
 */
public abstract class BaseReaperActivity<P extends BasePresenter> extends BaseMVPActivity<P> {

    @Override
    public void onInitDatas() {
        super.onInitDatas();
        ButterKnife.bind(this);
    }

    protected String getText(TextView tv) {
        return tv.getText().toString().trim();
    }

    //授权
    protected void authority() {
        SharePreferenceHelper.get().putIsLogin(true);
    }

    protected void animFinish() {
        finish();
    }

}
