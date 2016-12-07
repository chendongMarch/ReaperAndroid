package com.march.lib.platform.weibo;

import android.app.Activity;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.weibo
 * CreateAt : 2016/12/4
 * Describe :
 *
 * @author chendong
 */

public abstract class WeiboShareCreator {

    private Activity activity;
    private IWeiboShareAPI weiboShareAPI;

    public WeiboShareCreator(IWeiboShareAPI weiboShareAPI, Activity activity) {
        this.weiboShareAPI = weiboShareAPI;
        this.activity = activity;
    }

    public void shareLowerApi() {
        WeiboMessage message = new WeiboMessage();
        initLowerMsg(message);
        sendShareReq(activity, message);
    }

    public void shareUpperApi() {
        WeiboMultiMessage message = new WeiboMultiMessage();
        initUpperMsg(message);
        sendShareReq(activity, message);
    }

    public void initLowerMsg(WeiboMessage message) {
        // 只能分享 文字|图片|网页|视频|音乐 中的一种

        // 优先分享多媒体
        if (_getWebObj() != null) {
            message.mediaObject = _getWebObj();
            return;
        }
        if (_getVideoObj() != null) {
            message.mediaObject = _getVideoObj();
            return;
        }
        if (_getMusicObj() != null) {
            message.mediaObject = _getMusicObj();
            return;
        }

        if (_getTextObj() != null) {
            message.mediaObject = _getTextObj();
        }
    }

    public void initUpperMsg(WeiboMultiMessage message) {
        // 可以分享  文字+图片 + 网页|视频|音乐|声音
        if (_getTextObj() != null) {
            message.textObject = _getTextObj();
        }

        if (_getWebObj() != null) {
            message.mediaObject = _getWebObj();
            return;
        }
        if (_getVideoObj() != null) {
            message.mediaObject = _getVideoObj();
            return;
        }
        if (_getMusicObj() != null) {
            message.mediaObject = _getMusicObj();
            return;
        }
        if (_getVoiceObj() != null) {
            message.mediaObject = _getVoiceObj();
        }
    }

    private void sendShareReq(Activity activity, WeiboMessage weiboMessage) {
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
        weiboShareAPI.sendRequest(activity, request);
    }

    private void sendShareReq(Activity activity, WeiboMultiMessage weiboMessage) {
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        weiboShareAPI.sendRequest(activity, request);
    }

    public TextObject _getTextObj() {
        return null;
    }


    public WebpageObject _getWebObj() {
        return null;
    }


    public MusicObject _getMusicObj() {
        return null;
    }

    public VideoObject _getVideoObj() {
        return null;
    }

    public VoiceObject _getVoiceObj() {
        return null;
    }
}
