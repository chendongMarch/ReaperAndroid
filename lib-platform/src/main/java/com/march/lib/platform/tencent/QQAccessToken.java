package com.march.lib.platform.tencent;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.tencent
 * CreateAt : 2016/12/6
 * Describe :
 *
 * @author chendong
 */

public class QQAccessToken {
    int ret;
    String openid;
    String access_token;
    String pay_token;
    long expires_in;
    String pf;
    String pfkey;
    String msg;
    String login_cost;
    String query_authority_cost;
    String authority_cost;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getPay_token() {
        return pay_token;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getPfkey() {
        return pfkey;
    }

    public void setPfkey(String pfkey) {
        this.pfkey = pfkey;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLogin_cost() {
        return login_cost;
    }

    public void setLogin_cost(String login_cost) {
        this.login_cost = login_cost;
    }

    public String getQuery_authority_cost() {
        return query_authority_cost;
    }

    public void setQuery_authority_cost(String query_authority_cost) {
        this.query_authority_cost = query_authority_cost;
    }

    public String getAuthority_cost() {
        return authority_cost;
    }

    public void setAuthority_cost(String authority_cost) {
        this.authority_cost = authority_cost;
    }

    @Override
    public String toString() {
        return "QQAccessToken{" +
                "ret=" + ret +
                ", openid='" + openid + '\'' +
                ", access_token='" + access_token + '\'' +
                ", pay_token='" + pay_token + '\'' +
                ", expires_in=" + expires_in +
                ", pf='" + pf + '\'' +
                ", pfkey='" + pfkey + '\'' +
                ", msg='" + msg + '\'' +
                ", login_cost='" + login_cost + '\'' +
                ", query_authority_cost='" + query_authority_cost + '\'' +
                ", authority_cost='" + authority_cost + '\'' +
                '}';
    }
}
