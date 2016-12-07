package com.march.lib.platform.listener;

import com.march.lib.platform.exception.PlatformException;
import com.march.lib.platform.weibo.WbUserInfo;

public class OnWbLoginListener {
    public void onSucceed(WbUserInfo info){}

    public void onCancel(){}

    public void onException(PlatformException e){}
}