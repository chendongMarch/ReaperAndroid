package com.march.lib.platform.wx;

import android.content.Context;

import com.march.lib.platform.helper.AuthTokenKeeper;
import com.march.lib.platform.helper.GsonUtil;
import com.march.lib.platform.impl.WxPlatform;
import com.march.lib.platform.listener.OnWxLoginListener;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.wx
 * CreateAt : 2016/12/3
 * Describe :
 *
 * @author chendong
 */

public class WxLoginHelper {
    /*
     * 流程：
     * 发起登录申请流程 : 发起登录申请 -> code -> 获取access_token -> 存储 -> 获取用户信息 -> 结束
     * 检测本地token -> :
     * -> 没有 -> 发起登录申请流程
     * -> 有 -> 检测refresh_token有效期 —>
     *                               -> 快要到期 -> 发起登录申请流程
     *                               -> 还没到期 -> :
     *                                           -> 检测access_token有效性 —> :
     *                                                                  -> 有效 -> 获取用户信息
     *                                                                  -> 无效 -> 使用refresh_token刷新access_token -> :
     *                                                                                                             -> 成功 -> 存储 -> 获取用户信息 -> 结束
     *                                                                                                             -> refresh_token无效 -> 发起登录申请流程
     *
     */

    private static final String WX_TOKEN_STORE_KEY = "WX_TOKEN_STORE_KEY";
    private static final String WX_TOKEN_STORE_KEY_DATA = "WX_TOKEN_STORE_KEY_DATA";


    private Context context;
    private IWXAPI iwxapi;
    private String appId;


    private OnWxLoginListener loginListener;


    public WxLoginHelper(Context context, IWXAPI iwxapi, String appId) {
        this.context = context;
        this.iwxapi = iwxapi;
        this.appId = appId;
    }

    /**
     *
     * 开始登录
     */
    public void login(OnWxLoginListener loginListener) {
        this.loginListener = loginListener;
        // 检测本地token的机制
        WxAccessToken storeToken = AuthTokenKeeper.getWxToken(context);
        if (storeToken != null && storeToken.getAccess_token() != null) {
            // 检测refresh_token有效期，如果快到期了，强制刷新，换取新的refresh_token
            // 本地有token
            checkAccessTokenValid(storeToken);
        } else {
            // 本地没有token, 发起请求，wxEntry将会获得code，接着获取access_token
            sendAuthReq();
        }
    }

    /**
     * 发起申请
     */
    private void sendAuthReq() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "carjob_wx_login";
        iwxapi.sendReq(req);
    }

    /**
     * 刷新token,当access_token失效时使用
     *
     * @param resp 用来放 refresh_token
     */
    private void refreshToken(final WxAccessToken resp) {
        StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/refresh_token")
                .append("?appid=").append(appId)
                .append("&grant_type=").append("refresh_token")
                .append("&refresh_token=").append(resp.getRefresh_token());
        HttpsUtil.getHttps(sb.toString(), new HttpsUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                // 获取到access_token
                WxAccessToken wxGetAccessTokenResp = GsonUtil.getObject(result, WxAccessToken.class);
                if (wxGetAccessTokenResp.getErrcode() == 40030) {
                    // invalid refresh token
                    sendAuthReq();
                } else {
                    resp.initByRefreshToken(wxGetAccessTokenResp);
                    AuthTokenKeeper.saveWxToken(context, resp);
                    // 刷新完成，获取用户信息
                    getUserInfoByValidToken(resp);
                }
            }

            @Override
            public void onFailure(Exception exception) {
                // 刷新token失败
            }
        });
    }

    /**
     * 根据code获取access_token
     *
     * @param code code
     */
    public void getAccessTokenByCode(String code) {
        String secretCode = "";
        StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=").append(appId)
                .append("&secret=").append(secretCode)
                .append("&code=").append(code)
                .append("&grant_type=").append("authorization_code");
        HttpsUtil.getHttps(sb.toString(), new HttpsUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                // 获取到access_token
                WxAccessToken wxGetAccessTokenResp = GsonUtil.getObject(result, WxAccessToken.class);
                if (wxGetAccessTokenResp.getErrcode() == 40029) {
                    // invalid code
                } else {
                    AuthTokenKeeper.saveWxToken(context,wxGetAccessTokenResp);
                    getUserInfoByValidToken(wxGetAccessTokenResp);
                }
            }

            @Override
            public void onFailure(Exception exception) {
                // 获取access_token失败
            }
        });
    }

    /**
     * 检测token有效性
     *
     * @param resp 用来拿access_token
     */
    private void checkAccessTokenValid(final WxAccessToken resp) {
        StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/auth")
                .append("?access_token=").append(resp.getAccess_token())
                .append("&openid=").append(resp.getOpenid());

        HttpsUtil.getHttps(sb.toString(), new HttpsUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                // 检测是否有效
                WxCheckAccessTokenValidResp wxCheckAccessTokenValidResp = GsonUtil.getObject(result, WxCheckAccessTokenValidResp.class);
                if (wxCheckAccessTokenValidResp.getErrcode() == 0) {
                    // access_token有效。开始获取用户信息
                    getUserInfoByValidToken(resp);
                } else {
                    // access_token失效，刷新或者获取新的
                    refreshToken(resp);
                }
            }

            @Override
            public void onFailure(Exception exception) {
                // 检测access_token有效性失败
            }
        });
    }

    /**
     * token是ok的，获取用户信息
     *
     * @param resp 用来拿access_token
     */
    private void getUserInfoByValidToken(WxAccessToken resp) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + resp.getAccess_token() + "&openid=" + resp.getOpenid();

        HttpsUtil.getHttps(url, new HttpsUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                WxUserInfo wxUserInfo = GsonUtil.getObject(result, WxUserInfo.class);
                if (loginListener != null) {
                    loginListener.onSucceed(wxUserInfo);
                }
            }

            @Override
            public void onFailure(Exception exception) {
                // 获取用户信息失败
            }
        });
    }
}
