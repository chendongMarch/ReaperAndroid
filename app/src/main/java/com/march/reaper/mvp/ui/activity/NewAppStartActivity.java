package com.march.reaper.mvp.ui.activity;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.march.reaper.R;
import com.march.reaper.mvp.presenter.impl.NewAppStartPresenterImpl;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.utils.ImgLoadUtils;
import com.march.reaper.utils.SPUtils;
import com.march.reaper.utils.To;

import butterknife.Bind;
import butterknife.OnClick;

public class NewAppStartActivity extends RootActivity implements NewAppStartPresenterImpl.AppStartView {

    @Bind(R.id.et_name)
    EditText mNameEt;
    @Bind(R.id.iv_app_start_recommend)
    ImageView mRecommendIv;
    @Bind(R.id.app_start_title)
    TextView mTitleTv;
    @Bind(R.id.first_start_part)
    View mFirstStartView;
    private NewAppStartPresenterImpl mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.app_start_new_activity;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mPresenter = new NewAppStartPresenterImpl(this);
    }

    @Override
    protected void finalOperate() {
        super.finalOperate();
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

    private void startHomePage() {
        startActivity(HomePageActivity.class);
        finish();
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    protected void destroyPresenter() {
        mPresenter = null;
    }

    //加载图片到iv
    @Override
    public void loadViewImg(String url) {
        ImgLoadUtils.loadImg(self, url, mRecommendIv);
    }

    @OnClick({R.id.btn_register_device})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.btn_register_device:
                String name = getText(mNameEt);
                if (name.length() <= 0) {
                    To.show("填写称呼之后就可以进入了");
                    return;
                }
                mPresenter.registerByDeviceId(name);
                startHomePage();
                break;
        }
    }

//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(SucceedEntryAppEvent event) {
//        if(event.getMsg().equals(SucceedEntryAppEvent.EVENT_SUCCEED_ENTRY_APP)){
//            finish();
//        }
//    }

}