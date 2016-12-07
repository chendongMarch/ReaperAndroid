package com.march.lib.platform.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.march.lib.platform.Platform;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.weibo
 * CreateAt : 2016/12/7
 * Describe :
 *
 * @author chendong
 */

public class WeiboEntryActivity extends Activity implements IWeiboHandler.Response {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Platform.getInst().wb().onCreate(this, savedInstanceState, this);
        Log.e("chendong", "WeiboEntryActivity发起分享");
        Platform.getInst().wb().shareText(this, "test");
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        Platform.getInst().wb().handleWbResponse(baseResponse);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Platform.getInst().wb().onNewIntent(intent, this);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Platform.getInst().wb().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
