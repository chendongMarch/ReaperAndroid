package com.march.lib.platform.wx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.march.lib.platform.Platform;
import com.march.lib.platform.impl.WxPlatform;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

/**
 * Project  : Reaper
 * Package  : com.march.lib.platform.platform.other
 * CreateAt : 2016/12/3
 * Describe :
 *
 * @author chendong
 */
public abstract class AbsWXEntryActivity extends Activity implements IWXAPIEventHandler {

    private WxPlatform wxPlatform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxPlatform = getWxPlatform();
        wxPlatform.getWxApi().handleIntent(getIntent(), this);
    }

    protected WxPlatform getWxPlatform() {
        return Platform.getInst().wx();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxPlatform.getWxApi().handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp resp) {
        //从微信页面返回的数据
        Log.e("platform_wx", "onResp:" + "resp.getType():" + resp.getType() + "resp.errCode:" + resp.errCode + "resp.errStr:" + resp.errStr);
        wxPlatform.handleResp(resp);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        //发起微信请求将会经过的方法
        Log.e("platform_wx", "onReq: " + baseReq.toString());
    }
}
