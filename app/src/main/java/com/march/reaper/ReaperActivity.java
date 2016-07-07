package com.march.reaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * Created by march on 16/6/6.
 */
public abstract class ReaperActivity extends AppCompatActivity {

    protected Activity self;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        //全屏显示
        if (isFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(getLayoutId());
        hideActionBar();
        self = this;
        ButterKnife.bind(this);
        setIntentData(getIntent());
        initDatas();
        initViews(savedInstanceState);
        initEvents();
        finalOperate();
    }

    protected void setIntentData(Intent intent){

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

    protected abstract int getLayoutId();

    protected boolean isFullScreen() {
        return false;
    }

    protected boolean isNoActionBar() {
        return true;
    }

    public void startActivity(Class cls) {
        startActivity(new Intent(self,cls));
    }

    @Override protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }
}
