package com.march.reaper.mvp.ui.activity;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.march.reaper.R;
import com.march.reaper.common.API;
import com.march.reaper.common.SMSHelper;
import com.march.reaper.mvp.model.BaseResponse;
import com.march.reaper.mvp.ui.TitleActivity;
import com.march.reaper.mvp.ui.TitleFragment;
import com.march.reaper.mvp.ui.dialog.ChooseCodeModeDialog;
import com.march.reaper.utils.Lg;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.utils.To;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends TitleActivity {

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
    private Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.register_activity;
    }

    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        mTitleBar.setText("返回", "注册", null);
        mChooseCodeModeDialog = new ChooseCodeModeDialog(self);
    }


    @OnClick({R.id.btn_get_code, R.id.btn_regis})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code:
                phone = getText(mPhoneEt);
                if (!SMSHelper.get().isMobile(phone)) {
                    To.show("请输入合法的手机号.");
                    return;
                }
                mChooseCodeModeDialog.show(phone);
                break;
            case R.id.btn_regis:
                String code = getText(mCodeEt);
                pwd = getText(mPwdEt);
                if (code.length() <= 0 || code.length() > 4) {
                    To.show("验证码格式不正确,请检查.");
                    return;
                }
                if (pwd.length() <= 0 || pwd.length() > 10) {
                    To.show("密码格式不正确,请检查.");
                    return;
                }
                SMSHelper.get().submitCode(phone, code);
                break;
        }
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        SMSHelper.get().registerHandler(new SMSHelper.SmsResultListener() {
            @Override
            public void onGetCodeSucceed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        To.show("获取验证码成功,请稍候.");
                        mGetCodeBtn.setEnabled(false);
                        mGetCodeBtn.setBackgroundResource(R.drawable.shape_round_rect_gray);
                    }
                });
            }

            @Override
            public void onSubmitCodeSucceed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        registerToMyServer();
                    }
                });
            }

            @Override
            public void onGetVoiceSucceed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        To.show("获取语音验证码成功,请稍候.");
                    }
                });

            }

            @Override
            public void onError() {

            }
        });
    }

    //验证码验证通过,向服务器注册
    private void registerToMyServer() {
        HashMap<String, String> map = new HashMap<>();
        Lg.e(phone +"  " + pwd);
        map.put("username", phone);
        map.put("pwd", pwd);
        QueryUtils.get().post(API.POST_ADDUSER, BaseResponse.class, map, new QueryUtils.OnQueryOverListener<BaseResponse>() {
            @Override
            public void queryOver(BaseResponse rst) {
                if (rst.getStatus() == 901) {
                    To.show("您已经注册过了,快去登录吧");
                    Snackbar.make(mPwdEt,"您已经注册过了,快去登录吧",Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).show();

                }
                if (rst.getStatus() == 902) {
                    To.show("注册失败,请重试");
                    finish();
                }
                if (rst.getStatus() == 0) {
                    To.show("注册成功");
                    finish();
                }
            }

            @Override
            public void error(Exception e) {
                To.show("注册失败");
            }
        });
    }
}
