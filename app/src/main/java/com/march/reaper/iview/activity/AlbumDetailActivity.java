package com.march.reaper.iview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.march.lib.core.widget.TitleBarView;
import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.imodel.bean.BeautyAlbum;
import com.march.reaper.ipresenter.AlbumDetailPresenter2;
import com.march.reaper.widget.RecyclerGroupView;
import com.r0adkll.slidr.Slidr;

import butterknife.Bind;


/**
 * 专辑详情界面
 */
public class AlbumDetailActivity
        extends BaseReaperActivity<AlbumDetailPresenter2>
        implements AlbumDetailPresenter2.AlbumDetailView {


    @Bind(R.id.detail_albumlist_rv)
    RecyclerGroupView mRgv;
    public static final int TYPE_IS_COLLECTION = 0;
    public static final int TYPE_IS_DETAILS = 1;

    //展示收藏的图片
    public static void loadActivity4Collection(Activity activity) {
        Intent intent = new Intent(activity, AlbumDetailActivity.class);
        intent.putExtra(Constant.KEY_DETAIL_TYPE, TYPE_IS_COLLECTION);
        activity.startActivity(intent);
    }

    //展示某一个专辑的详情
    public static void loadActivity4DetailShow(Activity activity, BeautyAlbum album) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_DETAIL_TYPE, TYPE_IS_DETAILS);
        bundle.putParcelable(Constant.KEY_ALBUM_DETAIL_SHOW, album);
        Intent intent = buildIntent(activity, AlbumDetailActivity.class, bundle);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.detail_activity;
    }

    @Override
    public void onInitViews(View view, Bundle save) {
        super.onInitViews(view, save);
        Slidr.attach(this);

//        SwipeBackHelper.onCreate(this);
//
//        SwipeBackHelper.getCurrentPage(this)//获取当前页面
//                .setSwipeBackEnable(true)//设置是否可滑动
//                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
//                .setSwipeEdgePercent(0.5f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
//                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
//                .setScrimColor(Color.TRANSPARENT)//底层阴影颜色
//                .setClosePercent(0.8f)//触发关闭Activity百分比
//                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
//                .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
////                .setDisallowInterceptTouchEvent(true)//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
//                .addListener(new SwipeListener() {//滑动监听
//
//                    @Override
//                    public void onScroll(float percent, int px) {//滑动的百分比与距离
//                    }
//
//                    @Override
//                    public void onEdgeTouch() {//当开始滑动
//                    }
//
//                    @Override
//                    public void onScrollToClose() {//当滑动关闭
//                    }
//                });
        mTitleBarView.setText("首页", "专辑详情", "大图");
        mTitleBarView.setLeftBackListener(mActivity);
    }
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        SwipeBackHelper.onPostCreate(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        SwipeBackHelper.onDestroy(this);
//    }

    @Override
    public void onBackPressed() {
        animFinish();
    }

    @Override
    public void onStartWorks() {
        super.onStartWorks();
        mPresenter.justQuery();
    }

    @Override
    public void onInitEvents() {
        super.onInitEvents();
        mTitleBarView.setListener(TitleBarView.POS_Right, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.switchMode();
            }
        });
    }

    @Override
    public void setModeTvText(String txt) {
        mTitleBarView.get(TitleBarView.POS_Right).setText(txt);
    }

    @Override
    public void loadHeaderZoomView(String url) {
//        ImageHelper.loadImg(mContext, url, mZoomIv);
    }

    @Override
    protected AlbumDetailPresenter2 createPresenter() {
        return new AlbumDetailPresenter2();
    }

    @Override
    protected boolean isInitTitle() {
        return true;
    }

    @Override
    public RecyclerGroupView getRgv() {
        return mRgv;
    }
}
