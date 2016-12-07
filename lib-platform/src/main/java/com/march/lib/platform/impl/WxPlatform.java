package com.march.lib.platform.impl;

import android.content.Context;
import android.graphics.Bitmap;

import com.march.lib.platform.exception.PlatformException;
import com.march.lib.platform.helper.Util;
import com.march.lib.platform.listener.OnWxLoginListener;
import com.march.lib.platform.listener.OnWxShareListener;
import com.march.lib.platform.wx.WxLoginHelper;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXEmojiObject;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.platform.impl
 * CreateAt : 2016/12/3
 * Describe :
 *
 * @author chendong
 */
public class WxPlatform extends BasePlatform {

    public static final String TAG = WxPlatform.class.getSimpleName();

    public static final int CHAT = SendMessageToWX.Req.WXSceneSession;
    public static final int ZONE = SendMessageToWX.Req.WXSceneTimeline;
    public static final int FAVORITE = SendMessageToWX.Req.WXSceneFavorite;

    public static final int THUMB_SIZE = 150;

    private OnWxShareListener wxShareListener;
    private WxLoginHelper wxLoginHelper;
    private IWXAPI mWxApi;

    public WxPlatform(Context context,String appId, String appName) {
        super(appId, appName);
        regToWx(context);
    }


    private void regToWx(Context context) {
        mWxApi = WXAPIFactory.createWXAPI(context, appId, true);
        mWxApi.registerApp(appId);
    }

    public IWXAPI getWxApi() {
        return mWxApi;
    }

    public void login(Context context,String secret,OnWxLoginListener loginListener) {
        if (!mWxApi.isWXAppInstalled()) {
            // 客户端没有安装
            return;
        }
        if (!mWxApi.isWXAppSupportAPI()) {
            // 客户端不是最新版
            return;
        }

        wxLoginHelper = new WxLoginHelper(context,mWxApi,appId);
        wxLoginHelper.login(secret,loginListener);
    }


    public void setWxShareListener(OnWxShareListener wxShareListener) {
        this.wxShareListener = wxShareListener;
    }

    /**
     * 处理返回的response
     *
     * @param resp resp
     */
    public void handleResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            // 登录
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    // 用户同意
                    SendAuth.Resp authResp = (SendAuth.Resp) resp;
                    String auth_code = authResp.code;
                    wxLoginHelper.getAccessTokenByCode(auth_code);
//                    authResp.country;
//                    authResp.lang;
//                    authResp.state;
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    // 用户取消
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    // 用户拒绝授权
                    break;
            }
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            // 分享
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    // 分享成功
                    if (wxShareListener != null)
                        wxShareListener.onSuccess();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    // 分享取消
                    if (wxShareListener != null)
                        wxShareListener.onCancel();
                    break;
                case BaseResp.ErrCode.ERR_SENT_FAILED:
                    // 分享失败
                    if (wxShareListener != null)
                        wxShareListener.onFailure(new PlatformException("分享失败"));
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    // 分享被拒绝
                    if (wxShareListener != null)
                        wxShareListener.onFailure(new PlatformException("分享被拒绝"));
                    break;
            }
        }
    }

    private void sendMsgToWx(WXMediaMessage msg, int share2Where, String sign) {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(sign);
        req.message = msg;
        req.scene = share2Where;
        mWxApi.sendReq(req);
    }

    public void shareGif(String imagePath, int share2Where) {
        WXEmojiObject emoji = new WXEmojiObject();
        emoji.emojiPath = imagePath;//图片路径

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = emoji;
        //这个值似乎有限制,太大无法发送,所有已使用低质量压缩
        msg.setThumbImage(Util.getBitmapByLocalPath(imagePath, 32));

        sendMsgToWx(msg, share2Where, "emoji");
    }

    public void shareText(String text, int share2Where) {
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.title = "Will be ignored";
        msg.description = text;

        sendMsgToWx(msg, share2Where, "text");

    }

    private void shareBitmap(WXImageObject imgObj, Bitmap bmp, int share2Where) {
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        msg.setThumbImage(Util.createStaticSizeBitmap(bmp, 32));

        sendMsgToWx(msg, share2Where, "image");
    }

    public void shareImage(Bitmap bmp, int share2Where) {
        WXImageObject imgObj = new WXImageObject(bmp);
        shareBitmap(imgObj, bmp, share2Where);
    }

    // 不再集成网络图片下载的方法吗自己在外面下载好，不需要太大的
    public void shareImage(String path, final int share2Where) {
        final WXImageObject imgObj = new WXImageObject();
        imgObj.imagePath = path;
        Bitmap bmp = Util.getBitmapByLocalPath(path, 32);
        if (bmp != null)
            shareBitmap(imgObj, bmp, share2Where);

    }

    public void shareMusic(String musicUrl, String title, String desc, Bitmap thumb, int share2Where) {
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = musicUrl;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = title;
        msg.description = desc;

        msg.setThumbImage(Util.createStaticSizeBitmap(thumb, 32));

        sendMsgToWx(msg, share2Where, "music");
    }

    public void shareVideo(String videoUrl, String title, String desc, Bitmap thumb, int share2Where) {

        WXVideoObject video = new WXVideoObject();
        video.videoUrl = videoUrl;

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = title;
        msg.description = desc;
        msg.setThumbImage(Util.createStaticSizeBitmap(thumb, 32));

        sendMsgToWx(msg, share2Where, "video");

    }


    public void shareWebPage(String webUrl, String title, String desc, Bitmap thumb, int share2Where) {
        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = webUrl;
        WXMediaMessage msg = new WXMediaMessage(webPage);
        msg.title = title;
        msg.description = desc;
        msg.setThumbImage(Util.createStaticSizeBitmap(thumb, 32));

        sendMsgToWx(msg, share2Where, "webpage");

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
