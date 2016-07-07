package com.march.reaper.mvp.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.march.bean.AlbumDetail;
import com.march.reaper.R;
import com.march.reaper.ReaperActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.utils.DisplayUtils;
import com.march.reaper.utils.Lg;
import com.march.reaper.widget.SwipeFinishLayout;

import butterknife.Bind;

public class ScanImgActivity extends ReaperActivity {

    @Bind(R.id.scan_iv)
    ImageView mScanIv;
    @Bind(R.id.scan_swipe)
    SwipeFinishLayout mSwipeFinishLayout;
    private AlbumDetail mAlbumDetailData;

    @Override
    protected int getLayoutId() {
        return R.layout.scan_img_activity;
    }


    @Override
    protected void initDatas() {
        super.initDatas();
        mAlbumDetailData = (AlbumDetail) getIntent().getSerializableExtra(Constant.KEY_ALBUM_DETAIL_SCAN);
    }


    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
//        mSwipeFinishLayout.setTouchView(mScanIv);
//        float rate = mAlbumDetailData.getHeight() * 1.0f / mAlbumDetailData.getWidth();
//        mScanIv.getLayoutParams().height = (int) (DisplayUtils.getScreenWidth() * rate);

        Lg.e(mAlbumDetailData.getWidth() + "  " + DisplayUtils.getScreenWidth());
        String url = mAlbumDetailData.getPhoto_src().replaceAll("-\\d+x\\d+.jpg", ".jpg");
        Lg.e(url);
        Glide.with(self).load(url).crossFade().into(mScanIv);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mSwipeFinishLayout.setOnSwipeFinishListener(new SwipeFinishLayout.OnSwipeFinishListener() {
            @Override
            public void onSwipeFinish() {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
