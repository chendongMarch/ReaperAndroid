package com.march.reaper.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.march.reaper.widget.RecyclerGroupView;

/**
 * Created by march on 16/6/30.
 * Activity公共Presenter接口
 */
public abstract class ActivityPresenter extends NetLoadListPresenter{

    public ActivityPresenter(RecyclerGroupView mRecyclerGV, Activity mContext) {
        super(mRecyclerGV, mContext);
    }

    protected void onCreate(Bundle savedInstanceState){}

    public void setIntent(Intent intent){}

    protected void onResume(){}

    protected void onPause(){}

    protected void onStop(){}

    protected void onDestroy(){}

    public void switchMode(TextView tv){}
}
