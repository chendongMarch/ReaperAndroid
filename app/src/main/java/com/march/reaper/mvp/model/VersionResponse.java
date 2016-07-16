package com.march.reaper.mvp.model;

/**
 * Created by march on 16/7/16.
 */
public class VersionResponse extends BaseResponse {
    int versionCode;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
