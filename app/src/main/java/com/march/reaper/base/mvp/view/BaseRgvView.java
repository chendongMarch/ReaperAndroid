package com.march.reaper.base.mvp.view;

import com.march.lib.core.mvp.view.BaseView;
import com.march.reaper.widget.RecyclerGroupView;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.base.mvp.view
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public interface BaseRgvView extends BaseView {
    RecyclerGroupView getRgv();
}
