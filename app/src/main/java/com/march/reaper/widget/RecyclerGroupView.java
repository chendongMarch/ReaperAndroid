package com.march.reaper.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.march.reaper.R;

/**
 * Created by march on 16/7/10.
 * 组合了刷新,列表,功能键的控件
 */
public class RecyclerGroupView extends FrameLayout {
    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatBtn;
//    private PtrFrameLayout mPtrLy;
    private OnRefreshBeginListener mOnRefreshBeginListener;
    private View mDefaultHeader;

    public RecyclerGroupView(Context context) {
        this(context, null);
    }

    public RecyclerGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
//        inflate(context, R.layout.widget_recycler_group_view, this);
        inflate(context, R.layout.widget_recycler_group_view_with_swipe, this);
        if (isInEditMode()) return;
        initViews();
//        initPtrRefreshPart();
        initSwipeRefreshPart();
        initFabPart();
    }

    private void initViews() {
//        mPtrLy = (PtrFrameLayout) findViewById(R.id.widget_rgv_ptr);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.widget_rgv_ptr);
        mFloatBtn = (FloatingActionButton) findViewById(R.id.widget_rgv_fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.widget_rgv_rv);
//        mDefaultHeader = mPtrLy.getHeaderView();
    }


    private void initFabPart() {
        mFloatBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);
            }
        });
    }


    private void initSwipeRefreshPart() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mOnRefreshBeginListener != null) {
                    mOnRefreshBeginListener.onRefreshBegin();
                }
            }
        });
    }

    public void setOnRefreshBeginListener(OnRefreshBeginListener mOnRefreshBeginListener) {
        this.mOnRefreshBeginListener = mOnRefreshBeginListener;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public FloatingActionButton getFloatBtn() {
        return mFloatBtn;
    }


    public void refreshComplete() {
//        mPtrLy.refreshComplete();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void autoRefresh() {
//        mPtrLy.autoRefresh();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public interface OnRefreshBeginListener {
        void onRefreshBegin();
    }
}
