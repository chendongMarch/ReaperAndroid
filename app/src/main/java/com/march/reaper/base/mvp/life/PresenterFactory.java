package com.march.reaper.base.mvp.life;


import com.march.lib_base.presenter.BasePresenter;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.base.mvp.life
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */
public interface PresenterFactory<P extends BasePresenter> {
    P crate();
}