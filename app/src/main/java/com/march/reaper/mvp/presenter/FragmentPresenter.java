package com.march.reaper.mvp.presenter;

import android.app.Activity;

import com.march.reaper.widget.RecyclerGroupView;

/**
 * Created by march on 16/7/2.
 *
 */
public abstract class FragmentPresenter extends NetLoadListPresenter{
    public FragmentPresenter(RecyclerGroupView mRecyclerGV, Activity mContext) {
        super(mRecyclerGV, mContext);
    }
}
