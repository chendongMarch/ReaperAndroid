package com.march.reaper.mvp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.march.bean.WholeAlbumItem;
import com.march.reaper.R;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.common.API;
import com.march.reaper.mvp.model.WholeAlbumResponse;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.utils.SPUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * app启动页面
 */
public class AppStartActivity extends RootActivity {

    @Bind(R.id.iv_app_start_recommend)
    ImageView mRecommendIv;

    @Override
    protected int getLayoutId() {
        return R.layout.start_app_activity;
    }


    @Override
    protected void finalOperate() {
        super.finalOperate();
        final String appStartPhoto = SPUtils.get().getAppStartPhoto();
        if (appStartPhoto != null) {
            Glide.with(self).load(appStartPhoto).crossFade().into(mRecommendIv);
        }
        StringBuilder sb = new StringBuilder(API.GET_LUCKY).append("?limit=1");
        QueryUtils.get().query(sb.toString(), WholeAlbumResponse.class, new QueryUtils.OnQueryOverListener<WholeAlbumResponse>() {
            @Override
            public void queryOver(WholeAlbumResponse rst) {
                List<WholeAlbumItem> data = rst.getData();
                String album_cover = data.get(0).getAlbum_cover();
                if (appStartPhoto == null) {
                    Glide.with(self).load(album_cover).crossFade().into(mRecommendIv);
                }
                SPUtils.get().putAppStartPhoto(album_cover);
            }

            @Override
            public void error(Exception e) {

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(AppStartActivity.this, HomePageActivity.class));
                finish();
            }
        }, 5000);
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @OnClick(R.id.app_start_jump)
    public void clickBtn(View v) {
        startActivity(new Intent(AppStartActivity.this, HomePageActivity.class));
        finish();
    }
}
