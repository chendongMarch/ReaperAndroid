package com.march.reaper.mvp.ui.activity;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.march.reaper.R;
import com.march.reaper.mvp.presenter.impl.AppStartPresenterImpl;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.utils.ImgLoadUtils;
import com.march.reaper.utils.SPUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * app启动页面,包括注册登录的逻辑。不添加注册登录功能的在NewAppStartActivity
 */
public class AppStartActivity extends RootActivity
        implements AppStartPresenterImpl.AppStartView {

    @Bind(R.id.iv_app_start_recommend)
    ImageView mRecommendIv;
    @Bind(R.id.app_start_jump)
    TextView mJumpTv;
    @Bind(R.id.app_start_title)
    TextView mTitleTv;
    @Bind(R.id.app_start_logregis_part)
    ViewGroup mLogRegisterPartVg;
    private AppStartPresenterImpl mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.app_start_activity;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mPresenter = new AppStartPresenterImpl(this);
    }

    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
//        EventBus.getDefault().register(this);
        if (SPUtils.get().getIsLogin()) {
            mLogRegisterPartVg.setVisibility(View.GONE);
            mJumpTv.setVisibility(View.VISIBLE);
            mTitleTv.setVisibility(View.VISIBLE);
        } else {
            mLogRegisterPartVg.setVisibility(View.VISIBLE);
            mJumpTv.setVisibility(View.GONE);
            mTitleTv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void finalOperate() {
        super.finalOperate();
        mPresenter.queryAppStartFlashImg();
        if (SPUtils.get().getIsLogin()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //如果登录过了.直接进入主界面
                    startActivity(HomePageActivity.class);
                    finish();
                }
            }, 4000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ValueAnimator fade_in = ObjectAnimator.ofFloat(mLogRegisterPartVg, "alpha", 0.0f, 1.0f);
                    fade_in.setDuration(1500);
                    fade_in.setEvaluator(new FloatEvaluator());
                    fade_in.start();
                }
            }, 1000);
        }
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    protected void destroyPresenter() {
        mPresenter = null;
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

    //加载图片到iv
    @Override
    public void loadViewImg(String url) {
        ImgLoadUtils.loadImg(self, url, mRecommendIv);
    }


//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(SucceedEntryAppEvent event) {
//        if(event.getMsg().equals(SucceedEntryAppEvent.EVENT_SUCCEED_ENTRY_APP)){
//            finish();
//        }
//    }

}
