package com.march.lib.platform.impl;


/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.platform
 * CreateAt : 2016/12/3
 * Describe :
 *
 * @author chendong
 */
class BasePlatform {

    String appId = "";
    String appName = null;


    BasePlatform(String appId, String appName) {
        this.appId = appId;
        this.appName = appName;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
