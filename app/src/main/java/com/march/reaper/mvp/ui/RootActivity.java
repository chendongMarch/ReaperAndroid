package com.march.reaper.mvp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.march.reaper.common.SMSHelper;
import com.march.reaper.mvp.presenter.BasePresenter;
import com.march.reaper.utils.SPUtils;
import com.march.reaper.utils.To;

import butterknife.ButterKnife;

/**
 * Created by march on 16/6/6.
 * activity基类
 */
public abstract class RootActivity extends AppCompatActivity {

    protected Activity self;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        //全屏显示
        if (isFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (getLayoutView() == null)
            setContentView(getLayoutId());
        else
            setContentView(getLayoutView());
        hideActionBar();
        self = this;
        ButterKnife.bind(this);
        setIntentData(getIntent());
        initMainPresenter();
        initDatas();
        initViews(savedInstanceState);
        initEvents();
        finalOperate();
    }

    protected void initMainPresenter(){
    }

    protected void setIntentData(Intent intent) {

    }

    protected void finalOperate() {
    }

    protected void initEvents() {
    }

    protected void initViews(Bundle save) {
    }

    protected void initDatas() {
    }

    //隐藏actionbar
    private void hideActionBar() {
        if (isNoActionBar()) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            if (getActionBar() != null) {
                getActionBar().hide();
            }
        }
    }

    protected String getText(TextView tv) {
        return tv.getText().toString().trim();
    }

    protected abstract int getLayoutId();

    protected View getLayoutView() {
        return null;
    }

    protected boolean isFullScreen() {
        return false;
    }

    protected boolean isNoActionBar() {
        return true;
    }

    public void startActivity(Class cls) {
        startActivity(new Intent(self, cls));
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    protected boolean checkCode(String code) {
        if (code.length() != 4) {
            To.show("验证码格式(4位)不正确,请检查.");
            return false;
        }

        return true;
    }

    protected boolean checkPwd(String pwd) {
        if (pwd.length() <= 5 || pwd.length() > 10) {
            To.show("密码格式(6-10位)不正确,请检查.");
            return false;
        }
        return true;
    }

    protected boolean checkAccount(String phone) {
        if (!SMSHelper.get().isMobile(phone)) {
            To.show("手机号格式不正确,请检查.");
            return false;
        }
        return true;
    }

    //授权
    protected void authority() {
        SPUtils.get().putIsLogin(true);
    }
}
