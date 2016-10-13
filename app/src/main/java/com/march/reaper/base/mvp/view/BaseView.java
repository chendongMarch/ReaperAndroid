package com.march.reaper.base.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.base.mvp.view
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public interface BaseView{
    Context getContext();
    Activity getActivity();
    Intent getData();
}
