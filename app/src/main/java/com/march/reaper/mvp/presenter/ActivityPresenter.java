package com.march.reaper.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by march on 16/6/30.
 * Activity公共Presenter接口
 */
public abstract class ActivityPresenter {

    protected void onCreate(Bundle savedInstanceState){}

    protected void setIntent(Intent intent){}

    protected void onResume(){}

    protected void onPause(){}

    protected void onStop(){}

    protected void onDestroy(){}
}
