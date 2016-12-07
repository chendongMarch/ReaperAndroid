# 第三方登录 + 分享


## 前言

第三方登录和分享，一般每个app都会接入这个功能，来简化用户注册登录的流程，现在也有很多集成的平台，
比如umeng,shareSdk等等，极大的降低了了开发者接入的成本，不过也同样存在一些问题，相信大家也都遇到过，
所以我使用原生的SDK封装了一个Library，实现了登录和分享的相关功能，并尽可能的简化了调用API，
希望能简化三方接入的流程的同时实现高度的自定义，当然也有一些接入的时候遇到的坑，分享一下。



## 目前支持的平台

- 微信

- QQ

- 微博


## 初始化第三方平台

```java
        public static PlatformApi api;
        api = new PlatformApi();
        api.initWx(this, "wx87fa4edcc9bb3e84");
        api.initQQ(this, "1105571460");
        api.initWb(this, "3699350615");
```

## 快速集成微信分享

- 实现WXEntryActivity

```java
public class WXEntryActivity extends AbsWXEntryActivity {

}

```

- 注册

```java
        <!-- 微信专用Activity -->
        <activity
            android:name=".wxapi.WXEntryActivity"
            android:exported="true"
            android:label="@string/app_name" />
```

- 实现分享监听

```java
mWxPlatform.setWxShareListener(new WxShareListener() {
            @Override
            public void onSuccess() {
                Toaster.get().show(mContext, "分享成功");
            }

            @Override
            public void onFailure() {
                Toaster.get().show(mContext, "分享失败");
            }

            @Override
            public void onCancel() {
                Toaster.get().show(mContext, "分享取消");
            }
        });
```

- 开始分享

注意：无法分享本地视频，会导致点击之后打不开，音乐和网页也是如此

```java
// 分享文字
mWxPlatform.shareText("测试", wxShareType());
// 分享图片
mWxPlatform.shareImage(testBit, wxShareType());
// 分享本地图片
mWxPlatform.shareImage(localImagePath, wxShareType());
// 分享网络，需要先下载
ImageHelper.downloadPic(mContext, netImagePath, new ImageHelper.OnDownloadOverHandler() {
    @Override
    public void onSuccess(Bitmap bitmap) {
        mWxPlatform.shareImage(bitmap, wxShareType());
    }
});
// 分享gif动图，使用的是emoji表情
mWxPlatform.shareGif(localGifPath, wxShareType());
// 分享网络视频
mWxPlatform.shareVideo(netVideoPath, "video title", "video desc", res2Bitmap(), wxShareType());
// 分享网络音乐
mWxPlatform.shareMusic(netMusicPath, "music title", "music desc", res2Bitmap(), wxShareType());
// 分享网络网页
mWxPlatform.shareWebPage(testWebUrl, "web title", "web desc", res2Bitmap(), wxShareType());
```

