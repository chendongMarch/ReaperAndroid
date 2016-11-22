package com.march.reaper.common;

import com.march.reaper.imodel.BaseResponse;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.common
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public class EmptyRequestCallback<D extends BaseResponse> implements RequestCallback<D> {

    @Override
    public void onSucceed(D data) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
