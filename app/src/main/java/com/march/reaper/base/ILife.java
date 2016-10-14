package com.march.reaper.base;

import android.content.Intent;
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

    void onInitIntent(Bundle intent);

    void onInitIntent(Intent intent);

    void onInitDatas();

    void onInitViews(Bundle saveData);

    void onInitViews(View view, Bundle saveData);

    void onInitEvents();

    void onStartWorks();
}
