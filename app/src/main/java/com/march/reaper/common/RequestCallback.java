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

public interface RequestCallback<D extends BaseResponse> {
    void onSucceed(D data);

    void onError(Exception e);
}
