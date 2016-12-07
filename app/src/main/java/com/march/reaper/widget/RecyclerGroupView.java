package com.march.reaper.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.march.lib.core.common.DimensionHelper;
import com.march.reaper.R;

/**
 * Created by march on 16/7/10.
 * 组合了刷新,列表,功能键的控件
 */
public class RecyclerGroupView extends FrameLayout {
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatBtn;
    private OnRefreshBeginListener mOnRefreshBeginListener;

    public RecyclerGroupView(Context context) {
        this(context, null);
    }

    public RecyclerGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        inflate(context, R.layout.widget_recycler_group_view, this);
        if (isInEditMode()) return;
        initViews();
        initRefreshPart();
        initFabPart();
    }


    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mFloatBtn = (FloatingActionButton) findViewById(R.id.widget_rgv_fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.widget_rgv_rv);
    }



    private void initFabPart() {
        mFloatBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);
            }
        });
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.YELLOW);

        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);

        // 从start开始出现，到end停止，可以出现在中间
//        swipeRefreshLayout.setProgressViewOffset(true, 0, 300);
        swipeRefreshLayout.setDistanceToTriggerSync(DimensionHelper.getScreenHeight(mContext) / 5);
//        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
    }

    private void initRefreshPart() {
        initSwipeRefreshLayout();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        swipeRefreshLayout.setRefreshing(false);
    }

    public void autoRefresh() {
        swipeRefreshLayout.setRefreshing(true);
//        mPtrLy.autoRefresh();
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
