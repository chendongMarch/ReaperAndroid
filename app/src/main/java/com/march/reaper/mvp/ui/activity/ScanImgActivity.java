package com.march.reaper.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.march.bean.AlbumDetail;
import com.march.bean.DetailCollection;
import com.march.reaper.R;
import com.march.reaper.common.DbHelper;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.utils.DisplayUtils;
import com.march.reaper.utils.Lg;
import com.march.reaper.widget.SwipeFinishLayout;

import butterknife.Bind;

/**
 * 图片查看,可以缩放,基于photoview
 */
public class ScanImgActivity extends RootActivity {

    @Bind(R.id.scan_iv)
    ImageView mScanIv;
    @Bind(R.id.scan_is_collection)
    ImageView mIsColIv;
    @Bind(R.id.scan_swipe)
    SwipeFinishLayout mSwipeFinishLayout;
    private boolean isCollection;
    private AlbumDetail mAlbumDetailData;
    private DetailCollection mCol;

    @Override
    protected int getLayoutId() {
        return R.layout.scan_img_activity;
    }


    @Override
    protected void initDatas() {
        super.initDatas();
        mAlbumDetailData = (AlbumDetail) getIntent().getSerializableExtra(Constant.KEY_ALBUM_DETAIL_SCAN);
        mCol = new DetailCollection(mAlbumDetailData);
        isCollection = DbHelper.get().isDetailCollection(mCol);
    }


    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        Lg.e(mAlbumDetailData.getWidth() + "  " + DisplayUtils.getScreenWidth());
        String url = mAlbumDetailData.getPhoto_src().replaceAll("-\\d+x\\d+.jpg", ".jpg");
        Lg.e(url);
        Glide.with(self).load(url).crossFade().into(mScanIv);

        mIsColIv.setImageResource(isCollection ? R.drawable.ic_collection : R.drawable.ic_not_collection);
        mIsColIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollection) {
                    DbHelper.get().removeDetailCollection(mCol);
                    mIsColIv.setImageResource(R.drawable.ic_not_collection);
                } else {
                    DbHelper.get().addDetailCollection(mCol);
                    mIsColIv.setImageResource(R.drawable.ic_collection);
                }
            }
        });
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
