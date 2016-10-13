package com.march.reaper.base.mvp.presenter;

import com.march.reaper.base.mvp.view.BaseView;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.base.mvp.mPresenter
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public interface IPresenter<VIEW extends BaseView> {

    void attachView(VIEW view);

    void detachView();
}
