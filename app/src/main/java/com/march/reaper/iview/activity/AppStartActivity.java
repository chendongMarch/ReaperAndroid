package com.march.reaper.iview.activity;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.base.mvp.life.PresenterFactory;
import com.march.reaper.base.mvp.life.PresenterLoader;
import com.march.reaper.helper.ImageHelper;
import com.march.reaper.helper.Toaster;
import com.march.reaper.ipresenter.impl.AppStartPresenter;
import com.march.reaper.utils.SPUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class AppStartActivity
        extends BaseReaperActivity<AppStartPresenter.AppStartView, AppStartPresenter>
        implements AppStartPresenter.AppStartView {

    @Bind(R.id.et_name)
    EditText mNameEt;
    @Bind(R.id.iv_app_start_recommend)
    ImageView mRecommendIv;
    @Bind(R.id.app_start_title)
    TextView mTitleTv;
    @Bind(R.id.first_start_part)
    View mFirstStartView;

    @Override
    protected int getLayoutId() {
        return R.layout.app_start_new_activity;
    }

    @Override
    protected void onStartWorks() {
        super.onStartWorks();
        //加载图片
        mPresenter.queryAppStartFlashImg();
        final View tempView;
        Handler handler = new Handler();
        if (SPUtils.get().getIsLogin()) {
            mTitleTv.setVisibility(View.VISIBLE);
            mTitleTv.setText("Welcome    " + SPUtils.get().getUserName());
            //已经登录过了直接过,并且记录一次开启
            mPresenter.recordStartApp();
//            tempView = mTitleTv;
            //2.5秒后开启页面
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startHomePage();
                }
            }, 2500);
        } else {
            //没有登录过，devideid注册
            tempView = mFirstStartView;
            //1秒后动画开始
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ValueAnimator fade_in = ObjectAnimator.ofFloat(tempView, "alpha", 0.0f, 1.0f);
                    fade_in.setDuration(1500);
                    fade_in.setEvaluator(new FloatEvaluator());
                    fade_in.start();
                }
            }, 500);
        }
    }

    @Override
    protected PresenterLoader<AppStartPresenter> createPresenterLoader() {
        return new PresenterLoader<>(this, new PresenterFactory<AppStartPresenter>() {
            @Override
            public AppStartPresenter crate() {
                return new AppStartPresenter();
            }
        });
    }

    private void startHomePage() {
        startActivity(HomePageActivity.class);
        animFinish();
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }


    //加载图片到iv
    @Override
    public void loadViewImg(String url) {
        ImageHelper.loadImg(mContext, url, mRecommendIv);
    }

    @OnClick({R.id.btn_register_device})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.btn_register_device:
                String name = getText(mNameEt);
                if (name.length() <= 0) {
                    Toaster.get().show(mContext, "填写称呼之后就可以进入了");
                    return;
                }
                mPresenter.registerByDeviceId(name);
                startHomePage();
                break;
        }
    }

    @Override
    protected String[] getPermission2Check() {
        return new String[0];
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }
}