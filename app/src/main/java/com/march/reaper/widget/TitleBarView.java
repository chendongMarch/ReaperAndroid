package com.march.reaper.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.march.reaper.R;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.widget
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public class TitleBarView extends LinearLayout {

    public TitleBarView(Context context) {
        this(context, null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.widget_title_bar, this);
        if (isInEditMode()) return;
        initViews();
    }


    /**
     * 左边位置
     */
    public static final int POS_Left = 0;
    /**
     * 右边位置
     */
    public static final int POS_Right = 1;
    /**
     * 中间标题位置
     */
    public static final int POS_Center = 2;

    private TextView mLeftTv;
    private TextView mRightTv;
    private TextView mCenterTv;

    private void initViews() {
        setOrientation(LinearLayout.VERTICAL);
        mLeftTv = (TextView) findViewById(R.id.tv_titlebar_left);
        mRightTv = (TextView) findViewById(R.id.tv_titlebar_right);
        mCenterTv = (TextView) findViewById(R.id.tv_titlebar_center);
    }


    /**
     * 获取某个位置的TextView
     *
     * @param pos 位置
     * @return TextView
     */
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

    /**
     * 给某个位置的TextViews设置文本
     *
     * @param pos 位置
     * @param txt 文本
     */
    public void setText(int pos, String txt) {
        get(pos).setText(txt);
    }

    /**
     * 设置文本
     *
     * @param leftTxt   左边显示文本，null时不显示
     * @param centerTxt 中间显示文本，null时不显示
     * @param rightTxt  右边显示文本，null时不显示
     */
    public void setText(String leftTxt, String centerTxt, String rightTxt) {
        if (leftTxt != null)
            mLeftTv.setText(leftTxt);
        if (centerTxt != null)
            mCenterTv.setText(centerTxt);
        if (rightTxt != null)
            mRightTv.setText(rightTxt);
    }

    /**
     * 给某个位置的TextView设置监听
     *
     * @param pos      位置
     * @param listener 监听
     */
    public void setListener(int pos, View.OnClickListener listener) {
        get(pos).setOnClickListener(listener);
    }

    public void setLeftBackListener(final Activity activity) {
        mLeftTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }

    /**
     * 隐藏title
     */
    public void hide() {
        setVisibility(View.GONE);
    }

    /**
     * 隐藏某个位置的显示
     *
     * @param pos 位置
     */
    public void hide(int pos) {
        get(pos).setVisibility(View.GONE);
    }

}
