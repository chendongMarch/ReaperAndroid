package com.march.reaper.mvp.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.march.reaper.R;
import com.march.reaper.listener.OnDialogBtnListener;
import com.march.reaper.mvp.ui.RootDialog;

import butterknife.Bind;

/**
 * Created by march on 16/7/16.
 * 消息的dialog
 */
public class CommonMsgDialog extends RootDialog {
    public static final int Btn_OK = 0;
    public static final int Btn_CANCEL = 1;
    @Bind(R.id.tv_dialog_title)
    TextView mTitleTv;
    @Bind(R.id.tv_dialog_msg)
    TextView mMsgTv;
    @Bind(R.id.tv_dialog_ok)
    TextView mOkBtnTv;
    @Bind(R.id.tv_dialog_cancel)
    TextView mCancelBtnTv;


    public CommonMsgDialog(Context context) {
        super(context, R.style.dialog_theme);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_common_msg;
    }

    @Override
    protected void setWindowParams() {
        setWindowParams(WRAP, WRAP, Gravity.CENTER);
    }


    public CommonMsgDialog setBtn(int btnType, String txt, OnDialogBtnListener listener) {
        switch (btnType) {
            case Btn_CANCEL:
                setBtn(mOkBtnTv, txt, listener);
                break;
            case Btn_OK:
                setBtn(mCancelBtnTv, txt, listener);
                break;
        }
        return this;
    }


    //默认点击消失
    public CommonMsgDialog setBtn(int btnType, String txt) {
        switch (btnType) {
            case Btn_CANCEL:
                setBtn(mOkBtnTv, txt, null);
                break;
            case Btn_OK:
                setBtn(mCancelBtnTv, txt, null);
                break;
        }
        return this;
    }

    //默认点击消失,默认文本确定,取消
    public CommonMsgDialog setBtn(int btnType) {
        switch (btnType) {
            case Btn_CANCEL:
                setBtn(mCancelBtnTv, null, null);
                break;
            case Btn_OK:
                setBtn(mOkBtnTv, null, null);
                break;
        }
        return this;
    }


    private void setBtn(TextView btn, String txt, final OnDialogBtnListener listener) {
        btn.setVisibility(View.VISIBLE);

        if (txt != null)
            mCancelBtnTv.setText(txt);

        if (listener != null)
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBtnClick(CommonMsgDialog.this, (TextView) v);
                }
            });
        else
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
    }


    public void show(String title, String msg) {
        this.mTitleTv.setText(title);
        this.mMsgTv.setText(msg);
        show();
    }
}
