package com.march.lib.platform.exception;

import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.tauth.UiError;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.exception
 * CreateAt : 2016/12/5
 * Describe :
 *
 * @author chendong
 */
public class PlatformException extends Exception {
    private int type;
    WeiboException wbException;
    UiError qquiError;

    public PlatformException(String message) {
        super(message);
    }

    public PlatformException(String message, WeiboException wbException) {
        super(message);
        this.wbException = wbException;
    }

    public PlatformException(String message, UiError qquiError) {
        super(message);
        this.qquiError = qquiError;
    }

    public WeiboException getWbError() {
        return wbException;
    }

    public UiError getQQError() {
        return qquiError;
    }
}
