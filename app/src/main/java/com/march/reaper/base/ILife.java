package com.march.reaper.base;

import android.os.Bundle;
import android.view.View;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.base
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public interface ILife {

    void onInitIntent(Bundle bundle);

    void onInitDatas();

    void onInitViews(View view, Bundle saveData);

    void onInitEvents();

    void onStartWorks();
}
