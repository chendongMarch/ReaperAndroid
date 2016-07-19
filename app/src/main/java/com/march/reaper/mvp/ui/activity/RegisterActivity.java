package com.march.reaper.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.march.reaper.R;
import com.march.reaper.common.SMSHelper;
import com.march.reaper.listener.OnDialogBtnListener;
import com.march.reaper.mvp.contact.RegisterContact;
import com.march.reaper.mvp.presenter.impl.RegisterPresenterImpl;
import com.march.reaper.mvp.ui.RootDialog;
import com.march.reaper.mvp.ui.TitleActivity;
import com.march.reaper.mvp.ui.dialog.ChooseCodeModeDialog;
import com.march.reaper.mvp.ui.dialog.CommonMsgDialog;
import com.march.reaper.utils.To;

import butterknife.Bind;
import butterknife.OnClick;

public class RegisterActivity extends TitleActivity
        implements RegisterContact.RegisterView {

    @Bind(R.id.et_phone)
    EditText mPhoneEt;
    @Bind(R.id.et_code)
    EditText mCodeEt;
    @Bind(R.id.et_pwd)
    EditText mPwdEt;
    @Bind(R.id.btn_get_code)
    Button mGetCodeBtn;
    private ChooseCodeModeDialog mChooseCodeModeDialog;
    private String phone;
    private String pwd;
    private RegisterContact.RegisterPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.register_activity;
    }

    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        mTitleBar.setText("返回", "注册", null);
        mChooseCodeModeDialog = new ChooseCodeModeDialog(self);
        mPresenter.registerEventHandler();
    }

    @Override
    protected void initMainPresenter() {
        super.initMainPresenter();
        mPresenter = new RegisterPresenterImpl(this, this);
    }

    @OnClick({R.id.btn_get_code, R.id.btn_regis})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code:
                phone = getText(mPhoneEt);
                if (checkAccount(phone))
                    mChooseCodeModeDialog.show(phone);
                break;
            case R.id.btn_regis:
                String code = getText(mCodeEt);
                pwd = getText(mPwdEt);
                if (checkCode(code) && checkPwd(pwd))
                    SMSHelper.get().submitCode(phone, code);
                break;
        }
    }

    @Override
    protected void initEvents() {
        super.initEvents();

    }

    //验证码验证通过,向服务器注册
    public void registerToMyServer() {
        mPresenter.registerToMyServer(phone, pwd);
    }

    @Override
    public void registerSucceed() {
        To.show("注册成功");
        authority();
        startActivity(HomePageActivity.class);
        finish();
    }

    @Override
    public void userAlreadyExist() {
        new CommonMsgDialog(self)
                .setBtn(CommonMsgDialog.Btn_OK, "确定", new OnDialogBtnListener() {
                    @Override
                    public void onBtnClick(RootDialog dialog, TextView btn) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show("注册", "您已经注册过了,快去登录吧");
    }

    @Override
    public void registerFailed() {
        To.show("注册失败,请重试");
        finish();
    }

    @Override
    public void getCodeSucceed() {
        To.show("获取语音验证码成功,请稍候.");
    }

    @Override
    public void hideGetCodeButton() {
        To.show("获取验证码成功,请稍候.");
        mGetCodeBtn.setEnabled(false);
        mGetCodeBtn.setBackgroundResource(R.drawable.shape_round_rect_gray);
    }
}
