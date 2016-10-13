package com.march.reaper.iview.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.march.reaper.R;
import com.march.reaper.iview.RootActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class GuardActivity extends RootActivity {

    @Bind(R.id.et_guard)
    EditText mGuardEt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guard;
    }

    @Override
    protected void destroyPresenter() {

    }

    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
//        startActivity(NewAppStartActivity.class);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mGuardEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("91109123"))
                    startActivity(NewAppStartActivity.class);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.btn_check)
    public void clickBtn(View view) {
        if (getText(mGuardEt).equals("91109123"))
            startActivity(NewAppStartActivity.class);
    }
}
