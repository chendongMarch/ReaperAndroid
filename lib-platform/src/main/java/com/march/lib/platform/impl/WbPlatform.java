package com.march.lib.platform.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.march.lib.platform.Platform;
import com.march.lib.platform.exception.PlatformException;
import com.march.lib.platform.listener.OnWbLoginListener;
import com.march.lib.platform.listener.OnWbShareListener;
import com.march.lib.platform.weibo.StatusesAPI;
import com.march.lib.platform.weibo.WbAuthHelper;
import com.march.lib.platform.weibo.WbConstants;
import com.march.lib.platform.weibo.WbLoginHelper;
import com.march.lib.platform.weibo.WeiboShareCreator;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.utils.Utility;

import java.io.ByteArrayOutputStream;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.impl
 * CreateAt : 2016/12/3
 * Describe :
 *
 * @author chendong
 */
public class WbPlatform extends BasePlatform {

    private static final String TAG = WbPlatform.class.getSimpleName();
    private IWeiboShareAPI weiboShareAPI;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private StatusesAPI mStatusesAPI;
    private OnWbShareListener wbShareListener;

    private RequestListener requestListener = new RequestListener() {
        @Override
        public void onComplete(String s) {
            wbShareListener.onSuccess();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            wbShareListener.onFailure(new PlatformException("open api分享图片失败", e));
        }
    };

    private IWeiboHandler.Response shareResponse = new IWeiboHandler.Response() {
        @Override
        public void onResponse(BaseResponse baseResp) {
            if (baseResp != null) {
                switch (baseResp.errCode) {
                    case WBConstants.ErrorCode.ERR_OK:
                        // 分享成功
                        wbShareListener.onSuccess();
                        break;
                    case WBConstants.ErrorCode.ERR_CANCEL:
                        // 分享取消
                        wbShareListener.onCancel();
                        break;
                    case WBConstants.ErrorCode.ERR_FAIL:
                        // 分享失败
                        wbShareListener.onFailure(new PlatformException("微博分享失败"));
                        break;
                }
            }
        }
    };

    public WbPlatform(Context context, String appId, String appName) {
        super(appId, appName);
        weiboShareAPI = WeiboShareSDK.createWeiboAPI(context, appId);
        weiboShareAPI.registerApp();
        mAuthInfo = new AuthInfo(context, appId, WbConstants.REDIRECT_URL, WbConstants.SCOPE);
    }

    public void setWbShareListener(OnWbShareListener wbShareListener) {
        this.wbShareListener = wbShareListener;
    }


    public void login(Activity activity, OnWbLoginListener loginListener) {
        if (checkWeiboClientNotOk())
            return;
        if (mSsoHandler == null)
            mSsoHandler = new SsoHandler(activity, mAuthInfo);
        WbLoginHelper mLoginHelper = new WbLoginHelper(activity, appId);
        mLoginHelper.login(activity, mSsoHandler, loginListener);
    }

    // 授权时使用
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSsoHandler != null)
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
    }

    // 分享时在onCreate方法中使用
    public void handleWeiboResponse(Activity activity, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            weiboShareAPI.handleWeiboResponse(activity.getIntent(), shareResponse);
        }
    }

    // 分享时在onNewIntent方法中使用
    public void onNewIntent(Intent intent) {
        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        Platform.log(TAG,weiboShareAPI.handleWeiboResponse(intent, shareResponse));
    }


    private boolean checkWeiboClientNotOk() {
        if (wbShareListener == null) {
            Platform.log(TAG, "没有设置分享回调监听");
            return true;
        }
        if (!weiboShareAPI.isWeiboAppInstalled()) {
            return true;
        }
        if (!weiboShareAPI.isWeiboAppSupportAPI()
                || weiboShareAPI.getWeiboAppSupportAPI() == -1) {
            return true;
        }
        return false;
    }


    private void shareToWeiboClient(WeiboShareCreator handler) {
        if (checkWeiboClientNotOk()) {
            return;
        }
        int supportApi = weiboShareAPI.getWeiboAppSupportAPI();
        if (supportApi > 10531) {
            // 版本号支持文字+图片+多媒体(网页，音乐，视频，声音)的一种
            handler.shareLowerApi();
        } else {
            // 版本号只支持 文字|图片|多媒体(网页，音乐，视频，声音)的一种
            handler.shareUpperApi();
        }
    }

    // 授权，只拿token
    public void justAuth(final Activity activity, final Runnable runnable) {
        if (mSsoHandler != null && mStatusesAPI != null) {
            if (runnable != null)
                runnable.run();
            return;
        }
        mSsoHandler = new SsoHandler(activity, mAuthInfo);
        WbAuthHelper.auth(activity, mSsoHandler, new WbAuthHelper.OnAuthOverListener() {
            @Override
            public void onAuth(Oauth2AccessToken token) {
                Platform.log(TAG, token.toString());
                mStatusesAPI = new StatusesAPI(activity, appId, token);
                if (runnable != null)
                    runnable.run();
            }

            @Override
            public void onException(PlatformException e) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    // 默认分享只能支持32K以下，太鸡肋啦，用openApi实现
    public void shareImage(Activity activity, final String text, final Bitmap bitmap) {
        justAuth(activity, new Runnable() {
            @Override
            public void run() {
                mStatusesAPI.upload(text, bitmap, null, null, requestListener);
            }
        });
    }

    //分享文件，视频，gif等
    public void shareFile(final Activity activity, final String text, final ByteArrayOutputStream stream) {
        justAuth(activity, new Runnable() {
            @Override
            public void run() {
                mStatusesAPI.upload(text, stream, requestListener);
            }
        });
    }

    // 分享图片
    public void shareNetImage(final Activity activity, final String text, final String url) {
        justAuth(activity, new Runnable() {
            @Override
            public void run() {
                mStatusesAPI.uploadUrlText(text, url, null, null, null, requestListener);
            }
        });
    }


    // 分享文字
    public void shareText(Activity activity, final String text) {
        shareToWeiboClient(new WeiboShareCreator(weiboShareAPI, activity) {
            @Override
            public TextObject _getTextObj() {
                return getTextObj(text);
            }
        });
    }

    // 分享网页
    public void shareWebpage(Activity activity,
                             final String textContent,// 文字，如果是低版本不会带文字，优先分享网页
                             final String title,// 网页的标题
                             final String desc,// 网页描述
                             final Bitmap bitmap,// 左边缩略图
                             final String actionUrl,// 点击之后的url
                             final String defaultText) {
        shareToWeiboClient(new WeiboShareCreator(weiboShareAPI, activity) {
            @Override
            public WebpageObject _getWebObj() {
                return getWebpageObj(title, desc, bitmap, actionUrl, defaultText);
            }

            @Override
            public TextObject _getTextObj() {
                return getTextObj(textContent);
            }
        });
    }

    // 分享网页
    public void shareMusic(Activity activity, final String textContent, final String title, final String desc, final Bitmap bitmap, final String actionUrl, final String defaultText, final String dataUrl, final String hdDataUrl, final int duration) {
        shareToWeiboClient(new WeiboShareCreator(weiboShareAPI, activity) {
            @Override
            public MusicObject _getMusicObj() {
                return getMusicObj(title, desc, bitmap, actionUrl, defaultText, dataUrl, hdDataUrl, duration);
            }

            @Override
            public TextObject _getTextObj() {
                return getTextObj(textContent);
            }
        });
    }

    // 分享视频
    public void shareVideo(Activity activity, final String textContent, final String title, final String desc, final Bitmap bitmap, final String actionUrl, final String defaultText, final String dataUrl, final String hdDataUrl, final int duration) {
        shareToWeiboClient(new WeiboShareCreator(weiboShareAPI, activity) {
            @Override
            public VideoObject _getVideoObj() {
                return getVideoObj(title, desc, bitmap, actionUrl, defaultText, dataUrl, hdDataUrl, duration);
            }

            @Override
            public TextObject _getTextObj() {
                return getTextObj(textContent);
            }
        });
    }

    // 分享声音
    public void shareVoice(Activity activity, final String textContent, final String title, final String desc, final Bitmap bitmap, final String actionUrl, final String defaultText, final String dataUrl, final String hdDataUrl, final int duration) {
        shareToWeiboClient(new WeiboShareCreator(weiboShareAPI, activity) {

            @Override
            public VoiceObject _getVoiceObj() {
                return getVoiceObj(title, desc, bitmap, actionUrl, defaultText, dataUrl, hdDataUrl, duration);
            }

            @Override
            public TextObject _getTextObj() {
                return getTextObj(textContent);
            }
        });
    }


    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        return textObject;
    }


    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        imageObject.setImageObject(bitmap);
        return imageObject;
    }


    private WebpageObject getWebpageObj(String title, String desc, Bitmap bitmap, String actionUrl, String defaultText) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = desc;

        // 设置 Bitmap 类型的图片到视频对象里
        // 设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = actionUrl;
        mediaObject.defaultText = defaultText;
        return mediaObject;
    }


    private MusicObject getMusicObj(String title, String desc, Bitmap bitmap, String actionUrl, String defaultText, String dataUrl, String hdDataUrl, int duration) {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = title;
        musicObject.description = desc;

        // 设置 Bitmap 类型的图片到视频对象里
        // 设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        musicObject.setThumbImage(bitmap);
        musicObject.actionUrl = actionUrl;
        musicObject.dataUrl = dataUrl;
        musicObject.dataHdUrl = hdDataUrl;
        musicObject.duration = duration;
        musicObject.defaultText = defaultText;
        return musicObject;
    }

    private VideoObject getVideoObj(String title, String desc, Bitmap bitmap, String actionUrl, String defaultText, String dataUrl, String hdDataUrl, int duration) {
        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = title;
        videoObject.description = desc;
        videoObject.setThumbImage(bitmap);
        videoObject.actionUrl = actionUrl;
        videoObject.dataUrl = dataUrl;
        videoObject.dataHdUrl = hdDataUrl;
        videoObject.duration = duration;
        videoObject.defaultText = defaultText;
        return videoObject;
    }


    private VoiceObject getVoiceObj(String title, String desc, Bitmap bitmap, String actionUrl, String defaultText, String dataUrl, String hdDataUrl, int duration) {
        // 创建媒体消息
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title = title;
        voiceObject.description = defaultText;
        voiceObject.setThumbImage(bitmap);
        voiceObject.actionUrl = actionUrl;
        voiceObject.dataUrl = dataUrl;
        voiceObject.dataHdUrl = hdDataUrl;
        voiceObject.duration = duration;
        voiceObject.defaultText = defaultText;
        return voiceObject;
    }

}
