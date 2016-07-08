package com.march.reaper.mvp.ui.activity;

import android.content.Intent;
import android.os.Handler;

import com.march.reaper.R;
import com.march.reaper.RootActivity;

/**
 * app启动页面
 */
public class AppStartActivity extends RootActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.start_app_activity;
    }


    @Override
    protected void finalOperate() {
        super.finalOperate();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(AppStartActivity.this, HomePageActivity.class));
            }
        }, 800);
    }


}
