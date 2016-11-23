package com.march.reaper.base.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.lib.core.mvp.view.impl.BaseMVPActivity;
import com.march.reaper.helper.SharePreferenceHelper;
import com.march.reaper.iview.dialog.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Created by march on 16/6/6.
 * activity基类
 */
public abstract class BaseReaperActivity<P extends BasePresenter> extends BaseMVPActivity<P> {

    private LoadingDialog mLoadingDialog;

    @Override
    public void onInitDatas() {
        super.onInitDatas();
        ButterKnife.bind(this);
    }

    @Override
    public void onInitViews(View view, Bundle saveData) {
        super.onInitViews(view, saveData);
        mLoadingDialog = new LoadingDialog(mContext);
    }

    protected void showLoading(boolean isShow) {
        if (mLoadingDialog != null) {
            if (isShow && !mLoadingDialog.isShowing()) {
                mLoadingDialog.show();
            }
            if (!isShow && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        }
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
