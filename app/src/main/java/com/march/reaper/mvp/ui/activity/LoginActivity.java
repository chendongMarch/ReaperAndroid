package com.march.reaper.mvp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.march.reaper.R;
import com.march.reaper.mvp.ui.TitleActivity;

import butterknife.OnClick;

public class LoginActivity extends TitleActivity {

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
                break;
        }
    }

}
