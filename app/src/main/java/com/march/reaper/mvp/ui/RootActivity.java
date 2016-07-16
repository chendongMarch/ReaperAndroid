package com.march.reaper.mvp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by march on 16/6/6.
 */
public abstract class RootActivity extends AppCompatActivity {

    protected Activity self;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        //全屏显示
        if (isFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (getLayoutView() == null)
            setContentView(getLayoutId());
        else
            setContentView(getLayoutView());
        hideActionBar();
        self = this;
        ButterKnife.bind(this);
        setIntentData(getIntent());
        initDatas();
        initViews(savedInstanceState);
        initEvents();
        finalOperate();
    }

    protected void setIntentData(Intent intent) {

    }

    protected void finalOperate() {
    }

    protected void initEvents() {
    }

    protected void initViews(Bundle save) {
    }

    protected void initDatas() {
    }

    //隐藏actionbar
    private void hideActionBar() {
        if (isNoActionBar()) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            if (getActionBar() != null) {
                getActionBar().hide();
            }
        }
    }

    protected String getText(TextView tv){
        return tv.getText().toString().trim();
    }
    protected abstract int getLayoutId();

    protected View getLayoutView() {
        return null;
    }

    protected boolean isFullScreen() {
        return false;
    }

    protected boolean isNoActionBar() {
        return true;
    }

    public void startActivity(Class cls) {
        startActivity(new Intent(self, cls));
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }
}
