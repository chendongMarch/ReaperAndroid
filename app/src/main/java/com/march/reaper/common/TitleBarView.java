package com.march.reaper.common;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.march.reaper.R;

/**
 * Created by march on 16/7/9.
 *
 */
public class TitleBarView {

    public static final int POS_Left = 0;
    public static final int POS_Right = 1;
    public static final int POS_Center = 2;
    private TextView mLeftTv;
    private TextView mRightTv;
    private TextView mCenterTv;
    private ViewGroup mBarView;
    private Activity mActivity;

    public TitleBarView(Activity context, ViewGroup mBarView) {
        this.mActivity = context;
        this.mBarView = mBarView;
        mLeftTv = (TextView) mBarView.findViewById(R.id.tv_titlebar_left);
        mRightTv = (TextView) mBarView.findViewById(R.id.tv_titlebar_right);
        mCenterTv = (TextView) mBarView.findViewById(R.id.tv_titlebar_center);
        mLeftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
    }

    public TextView get(int pos) {
        switch (pos) {
            case POS_Left:
                return mLeftTv;
            case POS_Right:
                return mRightTv;
            case POS_Center:
                return mCenterTv;
        }
        return null;
    }

    public void setText(int pos, String txt) {
        get(pos).setText(txt);
    }

    public void setText(String leftTxt, String centerTxt, String rightTxt) {
        if (leftTxt != null)
            mLeftTv.setText(leftTxt);
        if (centerTxt != null)
            mCenterTv.setText(centerTxt);
        if (rightTxt != null)
            mRightTv.setText(rightTxt);
    }

    public void setListener(int pos, View.OnClickListener listener) {
        get(pos).setOnClickListener(listener);
    }

    public void clearBackListener() {
        get(POS_Left).setOnClickListener(null);
    }

    public void hide(){
        mBarView.setVisibility(View.GONE);
    }
}
