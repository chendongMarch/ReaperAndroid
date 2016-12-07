package com.march.lib.platform.tencent;

import com.march.lib.platform.exception.PlatformException;
import com.march.lib.platform.listener.OnQQShareListener;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.tencent
 * CreateAt : 2016/12/3
 * Describe :
 *
 * @author chendong
 */

public class ShareUiListener implements IUiListener {

    public static final int QQ_IMAGE_TEXT = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
    public static final int QQ_IMAGE = QQShare.SHARE_TO_QQ_TYPE_IMAGE;
    public static final int QQ_AUDIO = QQShare.SHARE_TO_QQ_TYPE_AUDIO;
    public static final int QQ_APP = QQShare.SHARE_TO_QQ_TYPE_APP;
    public static final int QZONE_IMAGE_TEXT = -QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;

    private int type;

    private OnQQShareListener listener;

    public ShareUiListener(OnQQShareListener listener) {
        this.listener = listener;
    }

    public ShareUiListener initType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public void onComplete(Object o) {
        doComplete(type, o);
    }

    @Override
    public void onError(UiError uiError) {
        listener.onFailure(new PlatformException("分享失败",uiError));
    }

    @Override
    public void onCancel() {
        listener.onCancel();
    }

    public void doComplete(int type, Object o) {
        listener.onSuccess();
    }
}
