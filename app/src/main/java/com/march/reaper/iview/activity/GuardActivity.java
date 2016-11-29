package com.march.reaper.iview.activity;

import android.os.Bundle;
import android.view.View;

import com.march.lib.core.common.Toaster;
import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.lib.view.LockView;
import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;

import butterknife.Bind;

public class GuardActivity extends BaseReaperActivity {

    @Bind(R.id.lockview)
    LockView mLockView;

    private String passWd2Check = "0124678";

    @Override
    protected int getLayoutId() {
        return R.layout.guard_activity;
    }


    @Override
    public void onInitViews(View view, Bundle saveData) {
        super.onInitViews(view, saveData);
    }

    @Override
    public void onInitEvents() {
        super.onInitEvents();
        mLockView.setListener(new LockView.OnLockFinishListener() {
            @Override
            public boolean onFinish(LockView lockView, String passWd, int passWsLength) {
                if(passWsLength<4){
                    Toaster.get().show(mContext, "请至少绘制四个点～");
                    return false;
                }else if(passWd.equals(passWd2Check)){
//                    Toaster.get().show(mContext, "请再次确认～");
                    startAppStart();
                    return true;
                }else {
                    Toaster.get().show(mContext, "密码错误请重新输入～");
                    return false;
                }
            }
        });
    }

    private void startAppStart() {
        startActivity(AppStartActivity.class);
        animFinish();
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }
}
