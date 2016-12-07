package com.march.lib.platform.wx;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.wx
 * CreateAt : 2016/12/3
 * Describe :
 *
 * @author chendong
 */

public class WxCheckAccessTokenValidResp {
    private int errcode;
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
