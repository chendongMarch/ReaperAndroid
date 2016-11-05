package com.march.reaper.iview.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.lib.core.presenter.BasePresenter;

import butterknife.Bind;
import butterknife.OnClick;

public class GuardActivity extends BaseReaperActivity {

    @Bind(R.id.et_guard)
    EditText mGuardEt;

    @Override
    protected int getLayoutId() {
        return R.layout.guard_activity;
    }


    @Override
    public void onInitEvents() {
        super.onInitEvents();
        mGuardEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("91109123"))
                    startAppStart();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void startAppStart() {
        startActivity(AppStartActivity.class);
        animFinish();
    }

    @OnClick(R.id.btn_check)
    public void clickBtn(View view) {
        if (getText(mGuardEt).equals("91109123")) {
            startAppStart();
        }
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
