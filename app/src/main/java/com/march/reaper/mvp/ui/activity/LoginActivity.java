package com.march.reaper.mvp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.march.reaper.R;
import com.march.reaper.common.API;
import com.march.reaper.common.SMSHelper;
import com.march.reaper.listener.OnDialogBtnListener;
import com.march.reaper.mvp.model.BaseResponse;
import com.march.reaper.mvp.ui.RootDialog;
import com.march.reaper.mvp.ui.TitleActivity;
import com.march.reaper.mvp.ui.dialog.CommonMsgDialog;
import com.march.reaper.utils.Lg;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.utils.To;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends TitleActivity {

    @Bind(R.id.et_phone)
    EditText mPhoneEt;
    @Bind(R.id.et_pwd)
    EditText mPwdEt;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        mTitleBar.setText("返回", "登录", null);
    }

    @OnClick({R.id.btn_login})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkToMyServer();
                break;
        }
    }

    //验证码验证通过,向服务器注册
    private void checkToMyServer() {
        String phone = getText(mPhoneEt);
        String pwd = getText(mPwdEt);
        if (!(checkPwd(pwd) && checkAccount(phone)))
            return;
        HashMap<String, String> map = new HashMap<>();
        Lg.e(phone + "  " + pwd);
        map.put("username", phone);
        map.put("pwd", pwd);
        QueryUtils.get().post(API.POST_CHECK_USER, BaseResponse.class, map, new QueryUtils.OnQueryOverListener<BaseResponse>() {
            @Override
            public void queryOver(BaseResponse rst) {
                if (rst.getStatus() == 903) {
                    To.show("用户名或密码不正确.");
                }
                if (rst.getStatus() == 0) {
                    To.show("登录成功");
                    authority();
                    startActivity(HomePageActivity.class);
                    finish();
                }
            }

            @Override
            public void error(Exception e) {
                To.show("登录失败");
            }
        });
    }

}
