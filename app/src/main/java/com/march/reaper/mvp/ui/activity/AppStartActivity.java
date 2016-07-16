package com.march.reaper.mvp.ui.activity;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.march.bean.WholeAlbumItem;
import com.march.reaper.R;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.common.API;
import com.march.reaper.mvp.model.WholeAlbumResponse;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * app启动页面
 */
public class AppStartActivity extends RootActivity {

    @Bind(R.id.iv_app_start_recommend)
    ImageView mRecommendIv;
    @Bind(R.id.app_start_jump)
    TextView mJumpTv;
    @Bind(R.id.app_start_title)
    TextView mTitleTv;
    @Bind(R.id.app_start_logregis_part)
    ViewGroup mLogRegisPartVg;

    @Override
    protected int getLayoutId() {
        return R.layout.start_app_activity;
    }


    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        if (SPUtils.get().getIsLogin()) {
            mLogRegisPartVg.setVisibility(View.GONE);
            mJumpTv.setVisibility(View.VISIBLE);
            mTitleTv.setVisibility(View.VISIBLE);
        } else {
            mLogRegisPartVg.setVisibility(View.VISIBLE);
            mJumpTv.setVisibility(View.GONE);
            mTitleTv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void finalOperate() {
        super.finalOperate();
        loadAppStartFlashImg();
        if (SPUtils.get().getIsLogin()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //如果登录过了.直接进入主界面
                    startActivity(new Intent(AppStartActivity.this, HomePageActivity.class));
                    finish();
                }
            }, 4000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ValueAnimator fade_in = ObjectAnimator.ofFloat(mLogRegisPartVg, "alpha", 0.0f, 1.0f);
                    fade_in.setDuration(1500);
                    fade_in.setEvaluator(new FloatEvaluator());
                    fade_in.start();
                }
            }, 1000);

        }

    }

    //加载图像
    private void loadAppStartFlashImg() {
        final String appStartPhoto = SPUtils.get().getAppStartPhoto();
        if (appStartPhoto != null) {
            Glide.with(self).load(appStartPhoto).crossFade().into(mRecommendIv);
        }
        final StringBuilder sb = new StringBuilder(API.GET_LUCKY).append("?limit=1");
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
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @OnClick({R.id.app_start_jump, R.id.btn_login, R.id.btn_regis})
    public void clickBtn(View v) {
        switch (v.getId()) {
            case R.id.app_start_jump:
                startActivity(HomePageActivity.class);
                finish();
                break;
            case R.id.btn_login:
                startActivity(LoginActivity.class);
                break;
            case R.id.btn_regis:
                startActivity(RegisterActivity.class);
                break;
        }

    }
}
