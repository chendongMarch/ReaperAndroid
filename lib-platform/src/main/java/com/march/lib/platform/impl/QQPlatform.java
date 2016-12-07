package com.march.lib.platform.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.march.lib.platform.Platform;
import com.march.lib.platform.listener.OnQQLoginListener;
import com.march.lib.platform.listener.OnQQShareListener;
import com.march.lib.platform.tencent.QQLoginHelper;
import com.march.lib.platform.tencent.ShareUiListener;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.impl
 * CreateAt : 2016/12/3
 * Describe : 问题汇总：com.mTencentApi.tauth.AuthActivity需要添加（ <data android:scheme="tencent110557146" />）否则会一直返回分享取消
 *
 * @author chendong
 */

public class QQPlatform extends BasePlatform {

    public static final String TAG = QQPlatform.class.getClass().getSimpleName();

    private Tencent mTencentApi;

    public static final int CHAT = 0;
    public static final int ZONE = 1;

    private ShareUiListener shareUiListener;
    private QQLoginHelper qqLoginHelper;


    public QQPlatform(Context context, String appId, String appName) {
        super(appId, appName);
        mTencentApi = Tencent.createInstance(appId, context);
    }

    public void setOnQQShareListener(OnQQShareListener qqShareListener) {
        this.shareUiListener = new ShareUiListener(qqShareListener);
    }


    public void login(Activity activity, OnQQLoginListener loginListener) {
        if (!mTencentApi.isSupportSSOLogin(activity)) {
            // 下载最新版
            return;
        }
        qqLoginHelper = new QQLoginHelper(activity, mTencentApi, loginListener);
        qqLoginHelper.login();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_OK) {
            if (requestCode == Constants.REQUEST_QQ_SHARE || requestCode == Constants.REQUEST_QZONE_SHARE) {
                Tencent.handleResultData(data, shareUiListener);
            }
            if (requestCode == Constants.REQUEST_LOGIN) {
                qqLoginHelper.handleResultData(data);
            }
        }
    }

    private Bundle buildCommonBundle(String title, String summary, String targetUrl, int share2Where) {
        final Bundle params = new Bundle();
        if (!TextUtils.isEmpty(targetUrl))
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        if (!TextUtils.isEmpty(summary))
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        if (!TextUtils.isEmpty(targetUrl))
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        Platform.log(TAG, appName);
        if (!TextUtils.isEmpty(appName))
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        // 加了这个会自动打开qq空间发布
        if (share2Where == ZONE)
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        return params;
    }

    private void putImagePath(Bundle params, String path) {
        if (!TextUtils.isEmpty(path))
            return;
        if (path.startsWith("http")) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, path);
        } else {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
        }
    }

    // 分享图文
    public void shareImageText(Activity activity, String title, String summary, String targetUrl, String imagePath, int share2Where) {
        final Bundle params = buildCommonBundle(title, summary, targetUrl, share2Where);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        // 本地或网络路径
        if (!TextUtils.isEmpty(imagePath))
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imagePath);
        mTencentApi.shareToQQ(activity, params, shareUiListener.initType(ShareUiListener.QQ_IMAGE_TEXT));
    }

    // 分享纯图片
    public void shareLocalImage(Activity activity, String localPath, int share2Where) {
        Bundle params = buildCommonBundle("", "", "", share2Where);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, localPath);
        mTencentApi.shareToQQ(activity, params, shareUiListener.initType(ShareUiListener.QQ_IMAGE));
    }

    // 分享音乐
    public void shareAudio(Activity activity, String title, String summary, String targetUrl, String imageUrl, String musicUrl, int share2Where) {
        final Bundle params = buildCommonBundle(title, summary, targetUrl, share2Where);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, musicUrl);
        mTencentApi.shareToQQ(activity, params, shareUiListener.initType(ShareUiListener.QQ_AUDIO));
    }

    // 分享app
    public void shareApp(Activity activity, String title, String summary, String targetUrl, String imageUrl, int share2Where) {
        final Bundle params = buildCommonBundle(title, summary, targetUrl, share2Where);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        mTencentApi.shareToQQ(activity, params, shareUiListener.initType(ShareUiListener.QQ_APP));
    }


    // qq空间分享图文
    public void shareImageTextQzone(Activity activity, String title, String summary, String targetUrl, String imagePath) {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);

        ArrayList<String> imageUrls = new ArrayList<>();
        if (!TextUtils.isEmpty(imagePath)) {
            if (!imagePath.startsWith("http")) {
                //本地文件
                imageUrls.add(imagePath.trim());
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
            } else {
                //网络文件
                imageUrls.add(imagePath.trim());
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
            }
        }
        if (!TextUtils.isEmpty(title))
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        if (!TextUtils.isEmpty(summary))
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);

        if (targetUrl == null) {
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "");
        } else {
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        }
        mTencentApi.shareToQzone(activity, params, shareUiListener.initType(ShareUiListener.QZONE_IMAGE_TEXT));
    }

}
