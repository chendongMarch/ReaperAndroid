package com.march.reaper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.march.lib.core.common.Logger;
import com.march.lib.view.LeProgressView;
import com.march.reaper.R;
import com.zhy.view.flowlayout.FlowLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Project  : CdLibsTest
 * Package  : com.march.cdlibstest.widget
 * CreateAt : 2016/11/22
 * Describe :
 *
 * @author chendong
 */

public class RefreshHeader extends FrameLayout implements PtrUIHandler {

    @Bind(R.id.lpv_progress)
    LeProgressView mLpv;

    public RefreshHeader(Context context) {
        this(context, null);
    }

    public RefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_refresh_header, this, true);
        ButterKnife.bind(this);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        Logger.e("onUIReset");
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        Logger.e("onUIRefreshPrepare");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        Logger.e("onUIRefreshBegin");
        mLpv.startLoading();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        Logger.e("onUIRefreshComplete");
        mLpv.stopLoading();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        if (isUnderTouch) {
            float currentPercent = ptrIndicator.getCurrentPercent();
            float delay = -0.7f;
            float speed = 2.0f;
            float percent = (currentPercent + delay) * speed;
            mLpv.prepareLoading(percent);
        }
    }
}
