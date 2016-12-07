package com.march.lib.platform.wx;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.wx
 * CreateAt : 2016/12/3
 * Describe :
 *
 * @author chendong
 */
public class WxAccessToken {
    //正确返回
    private String access_token;//接口调用凭证
    private long expires_in;//access_token接口调用凭证超时时间，单位（秒）。
    private String refresh_token;//用户刷新access_token。
    private String openid;//授权用户唯一标识。
    private String unionid;
    private String scope;//用户授权的作用域，使用逗号（,）分隔

    //错误返回
    private long errcode;

    private String errmsg;

    public void initByRefreshToken(WxAccessToken resp) {
        this.access_token = resp.access_token;
        this.expires_in = resp.expires_in;
        this.refresh_token = resp.refresh_token;
        this.openid = resp.openid;
        this.scope = resp.scope;
    }

    public long getErrcode() {
        return errcode;
    }

    public void setErrcode(long errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}