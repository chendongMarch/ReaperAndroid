package com.march.reaper.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.march.bean.AlbumDetail;
import com.march.bean.Detail;
import com.march.bean.DetailCollection;
import com.march.reaper.R;
import com.march.reaper.RootApplication;
import com.march.reaper.common.DbHelper;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.utils.DisplayUtils;
import com.march.reaper.utils.Lg;
import com.march.reaper.utils.To;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 图片查看,可以缩放,基于photoview
 */
public class ScanImgActivity extends RootActivity {

    @Bind(R.id.scan_iv)
    ImageView mScanIv;
    @Bind(R.id.scan_is_collection)
    ImageView mIsColIv;
    private boolean isCollection;
    private Detail mAlbumDetailData;
    private DetailCollection mCol;

    @Override
    protected int getLayoutId() {
        return R.layout.scan_img_activity;
    }

    @Override
    protected void destroyPresenter() {

    }


    public static void loadActivity(Activity activity, Detail detail) {
        Intent intent = new Intent(activity, ScanImgActivity.class);
        intent.putExtra(Constant.KEY_ALBUM_DETAIL_SCAN, detail);
        activity.startActivity(intent);
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
    }


    @OnClick({R.id.scan_is_download, R.id.scan_is_collection})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.scan_is_download:
                downloadPic();
                break;
            case R.id.scan_is_collection:
                if (isCollection) {
                    DbHelper.get().removeDetailCollection(mCol);
                    mIsColIv.setImageResource(R.drawable.ic_not_collection);
                } else {
                    DbHelper.get().addDetailCollection(mCol);
                    mIsColIv.setImageResource(R.drawable.ic_collection);
                }
                isCollection = !isCollection;
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }


    //下载图片
    private void downloadPic() {
        Glide.with(RootApplication.get().getApplicationContext())
                .load(mAlbumDetailData.getPhoto_src())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        File downloadDir = RootApplication.get().getDownloadDir();
                        String fileName = "reaper_" + System.currentTimeMillis() + ".jpg";
                        try {
                            File file = new File(downloadDir, fileName);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
                            To.show("图片下载完毕,保存到 " + file.getAbsolutePath());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
