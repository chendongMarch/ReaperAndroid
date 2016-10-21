package com.march.reaper.base;

import android.os.Bundle;
import android.view.View;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.base
 * CreateAt : 2016/10/13
 * Describe : 数据处理，页面加载的自定义生命周期，activity和fragment公用
 *
 * @author chendong
 */
public interface ILife {

    // 初始化传输数据
    void onInitIntent(Bundle bundle);
    // 初始化数据
    void onInitDatas();
    // 初始化控件
    void onInitViews(View view, Bundle saveData);
    // 初始化事件
    void onInitEvents();
    // 开始处理
    void onStartWorks();
}
