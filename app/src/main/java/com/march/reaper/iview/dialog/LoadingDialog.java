package com.march.reaper.iview.dialog;

import android.content.Context;
import android.view.Gravity;

import com.march.lib.core.dialog.BaseDialog;
import com.march.reaper.R;
import com.march.reaper.widget.LeProgressView;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview
 * CreateAt : 2016/11/22
 * Describe :
 *
 * @author chendong
 */
public class LoadingDialog extends BaseDialog {
    private LeProgressView mLpv;
    private boolean isFirstDismiss = true;

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_theme);
    }

    @Override
    protected void initViews() {
        mLpv = getView(R.id.lpv_progress);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void setWindowParams() {
        setWindowParams(WRAP, WRAP, 1, 0.3f, Gravity.CENTER);
    }

    @Override
    public void show() {
        super.show();
        mLpv.startLoading();
    }


    @Override
    public void dismiss() {
        if (isFirstDismiss) {
            mLpv.stopLoading(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            });
            isFirstDismiss = false;
        } else {
            super.dismiss();
            isFirstDismiss = true;
        }
    }
}
