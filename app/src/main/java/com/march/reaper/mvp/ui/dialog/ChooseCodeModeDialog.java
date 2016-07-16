package com.march.reaper.mvp.ui.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.march.reaper.R;
import com.march.reaper.common.SMSHelper;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.mvp.ui.RootDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by march on 16/7/6.
 * 宝宝添加和修改页面的弹窗
 */
public class ChooseCodeModeDialog extends RootDialog {

    private String phone;
    @Bind(R.id.tv_phone)
    TextView mPhoneTv;

    public ChooseCodeModeDialog(Context context) {
        super(context, R.style.dialog_theme);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_choose_voice_or_code;
    }

    @Override
    protected void setWindowParams() {
        setWindowParams(WRAP,WRAP,Gravity.CENTER);
    }

    @OnClick({R.id.tv_voice_code, R.id.tv_msg_code})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.tv_voice_code:
                SMSHelper.get().getVoiceCode(phone);
                break;
            case R.id.tv_msg_code:
                SMSHelper.get().getCode(phone);
                break;
        }
        dismiss();
    }

    public void show(String phone) {
        this.phone = phone;
        mPhoneTv.setText("验证方式(" + phone + ")");
        show();
    }
}
