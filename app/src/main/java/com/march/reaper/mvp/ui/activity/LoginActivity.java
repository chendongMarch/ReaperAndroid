package com.march.reaper.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.march.reaper.R;
import com.march.reaper.mvp.presenter.impl.LoginPresenterImpl;
import com.march.reaper.mvp.ui.TitleActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity
        extends TitleActivity
        implements LoginPresenterImpl.LoginView {

    private LoginPresenterImpl mPresenter;
    @Bind(R.id.et_phone)
    EditText mPhoneEt;
    @Bind(R.id.et_pwd)
    EditText mPwdEt;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void destroyPresenter() {
        mPresenter = null;
    }

    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        mPresenter = new LoginPresenterImpl(this);
        mTitleBar.setText("返回", "登录", null);
    }

    @Override
    protected void initMainPresenter() {
        super.initMainPresenter();

    }

    @OnClick({R.id.btn_login})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String phone = getText(mPhoneEt);
                String pwd = getText(mPwdEt);
                if (!(checkPwd(pwd) && checkAccount(phone)))
                    return;
                mPresenter.checkToMyServer(phone, pwd);
                break;
        }
    }

}
