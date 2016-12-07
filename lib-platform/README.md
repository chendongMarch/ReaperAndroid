## 前言

第三方登录和分享，一般每个app都会接入这个功能，来简化用户注册登录的流程，现在也有很多集成的平台，
比如umeng,shareSdk等等，极大的降低了了开发者接入的成本，不过也同样存在一些问题，相信大家也都遇到过。

所以我使用原生的SDK封装了一个Library，实现了登录和分享的相关功能，并尽可能的简化了调用API，
希望能简化三方接入的流程的同时实现高度的自定义，当然也有一些接入的时候遇到的坑，分享一下。

目前支持微信、微博、QQ，SDK已经集成在Lib里面，下载之后直接以来module即可


## 目录

- [开始](#开始)

- [微信快速接入](#微信快速接入)
	- [微信分享](#微信分享)
	- [微信登录](#微信登录)

- [QQ快速接入](#QQ快速接入)
	- [QQ分享](#QQ分享)
	- [QQ登录](#QQ登录)

- [微博快速接入](#微博快速接入)
	- [微博分享](#微博分享)
	- [微博 openApi 分享](#微博-openapi-分享)
	- [微博登录](#微博登录)

- [结束](#结束)


------

## 开始
- 首先你应该去每个平台申请appId或者appKey,这个是免不了的，每个平台不一样这里统称AppId

```java
String qqAppId = "110x571x60";
String wxAppId = "wx8xfa4edccxbb3e84";
String wbAppId = "36x93x0615";

// 初始化每个平台
Platform.newInst(this);
Platform.getInst().initWx(this, wxAppId);
Platform.getInst().initQQ(this, qqAppId);
Platform.getInst().initWb(this, wbAppId);

// or 一次初始化三个平台
Platform.getInst().init(this, qqAppId,wxAppId,wbAppId);

// 获取各个平台的API
mWxPlatform = Platform.getInst().wx();
mWbPlatform = Platform.getInst().wb();
mQqPlatform = Platform.getInst().qq();
```


------


## 微信快速接入
- 微信接入前需要准备一些工作，按照微信官方的文档来说，你需要在你的app的包下建立一个wxapi的包并创建`WxEntryActivity.class`，比如你的工程包名是`com.test.package`,那就是在`com.test.package.wxapi`包下面建立`WxEntryActivity.class`，并在`AndroidManifest.xml`,注册该Activity,它将作为微信分享的回调页。

```java
<!-- 微信专用Activity -->
<activity
     android:name=".wxapi.WXEntryActivity"
     android:exported="true"
     android:label="@string/app_name" />
```

- 为了简化开发，我已经写好一个`AbsWxEntryActivity`,你可以采用最简单的方式直接继承这个Activity，如下，当然你可能需要所有的Activity都实现你自己写好的的基类，那可以拷贝`AbsWXEntryActivity`里面的内容到你创建的类中即可。

```java
package com.march.reaper.wxapi;
import com.march.lib.platform.wx.AbsWXEntryActivity;
public class WXEntryActivity extends AbsWXEntryActivity {
    // 啥都不用写
}
```
### 微信分享
- 在类库中已经写好常用的分享方法，目前支持文本|Bitmap|本地图片文件|GIF动图(emoji)|网络视频|网络音频|网页

- 说明一下几个需要注意的地方
	- 视频音频之类的可以在聊天列表中之间点击播放，但是不能分享本地音视频资源，不然能分享成功，点击之后也是无效的
	- 网络图片需要分享的话需要先进行下载之后再分享

- 实现分享回调监听,监测分享的结果

```java
mWxPlatform.setWxShareListener(new OnWxShareListener() {
            @Override
            public void onSuccess() {
                toast("分享成功");
            }

            @Override
            public void onFailure(PlatformException e) {
                toast("分享失败");
            }

            @Override
            public void onCancel() {
                toast("分享取消");
            }
        });
```

- 分享类型。微信分享支持分享到好友，群，朋友圈，收藏,使用常量来表示

```java
 WxPlatform.ZONE 朋友圈
 WxPlatform.CHAT 好友和群
 WxPlatform.FAVORITE 收藏
```

- 直接用代码罗列一下API

```java
// 分享文本
mWxPlatform.shareText("测试",  WxPlatform.CHAT);
// 分享bitmap,网络图片请先下载
mWxPlatform.shareImage(testBit, WxPlatform.CHAT);
// 本地图片，本质还是bitmap，封装了一下
mWxPlatform.shareImage(localImagePath,  WxPlatform.CHAT);
// 分享Gif或emoji表情
mWxPlatform.shareGif(localGifPath,  WxPlatform.CHAT);
// 分享视频
mWxPlatform.shareVideo(netVideoPath, "video title", "video desc", thumbBit, WxPlatform.CHAT);
// 分享音乐
mWxPlatform.shareMusic(netMusicPath, "music title", "music desc", thumbBit, WxPlatform.CHAT);
// 分享网页
mWxPlatform.shareWebPage(testWebUrl, "web title", "web desc", thumbBit, WxPlatform.CHAT);
```

### 微信登录
- 微信登录需要请求access_token,获取token,刷新token,token持久化存储等相关功能已经封装在Libs里面
- 登录功能需要secretKey，另外现在微信登录居然要开发者认证，¥300。。不过我还是测试过啦。。。

```java
mWxPlatform.login(mContext, secretKey, new OnWxLoginListener() {
                    @Override
                    public void onSucceed(WxUserInfo info) {
                    	// 获取到结果
                        mInfoTv.setText(info.toString());
                    }

                    @Override
                    public void onException(PlatformException e) {
                        log(e.getMessage());
                    }
                });
```



------


## QQ快速接入
- 接入QQ之前的准备工作,在`AndroidMenifest.xml`中注册相关Activity,在`<data android:scheme="tencent11x5x71460" />`要加入tencent(AppId), 注意

```java
<!-- QQ专用Activity -->
<activity
    android:name="com.tencent.tauth.AuthActivity"
    android:launchMode="singleTask"
    android:noHistory="true">
    <intent-filter>
        <action android:name="android.intent.action.V
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSA
        <data android:scheme="tencent11x5x71460" />
    </intent-filter>
</activity>
<activity
    android:name="com.tencent.connect.common.AssistActivity"
    android:configChanges="orientation|keyboardHidden|screenSize"
    android:theme="@android:style/Theme.Translucent.NoTitleBar" />
```

### QQ分享
- 在`onActivityResult()`中接受数据进行处理

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    mQqPlatform.onActivityResult(requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);
}
```
- 目前支持分享到群，好友，空间

```java
QQPlatform.ZONE qq空间
QQPlatform.CHAT 好友和群
```

- 设置分享的监听事件

```java
mQqPlatform.setOnQQShareListener(new OnQQShareListener() {
            @Override
            public void onSuccess() {
                toast("分享成功");
            }

            @Override
            public void onFailure(PlatformException e) {
                toast("分享失败 " + e.getQQError().errorDetail);
            }

            @Override
            public void onCancel() {
                toast("分享取消");
            }

        });
```

- 列举一下支持的分享类型

```java
// 分享本地图片
mQqPlatform.shareLocalImage(mActivity, localImagePath, QQPlatform.CHAT);
// 分享图文
mQqPlatform.shareImageText(mActivity, "title", "summary", testWebUrl, localImagePath, QQPlatform.CHAT);
// 分享app,会打开app在应用宝的下载地址
mQqPlatform.shareApp(mActivity, "title", "summary", testWebUrl, localImagePath, QQPlatform.CHAT);
// 分享音乐
mQqPlatform.shareAudio(mActivity, "title", "summary", testWebUrl, localImagePath, netMusicPath, QQPlatform.CHAT);
```
### QQ登录
- 一行代码接入

```java
mQqPlatform.login(mActivity, new OnQQLoginListener() {
                    @Override
                    public void onException(PlatformException e) {
                        log(e.getQQError().errorMessage + "  " + e.getQQError().errorDetail);
                    }

                    @Override
                    public void onSucceed(QQUserInfo userInfo) {
                        log(userInfo);

                    }
                });
```


------



## 微博快速接入
- 微博接入比较麻烦，需要配置的地方很多，坑也特别多。
- 需要注意的地方是有个回调的url,会在微博管理台那里配置，请使用默认的那个url

### 微博分享
- 1.首先在`AndroidManifest.xml`中配置你的发起分享的Activity，如下,不然没办法接收到成功或者失败的回调

```java
<activity
    android:name=".TestPlatformActivity"
    android:configChanges="keyboardHidden|orientation"
    android:screenOrientation="portrait">
    <intent-filter>
        <action android:name="com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

- 2.设置监听

```java
mWbPlatform.setWbShareListener(new OnWbShareListener() {
            @Override
            public void onSuccess() {
                toast("分享成功");
            }

            @Override
            public void onFailure(PlatformException e) {
                toast("分享失败");
                e.getWbError().printStackTrace();
            }

            @Override
            public void onCancel() {
                toast("分享取消");
            }
        });
```

- 3.微博分享需要发起分享的Activity实现`IWeiboHandler.Response`接口，这里要说一下为什么没有把这个监听拿到类库里面，因为必须是Activity实现这个监听，不然没办法获取到分享的结果，真的很坑，所以只能拿出来自己实现啦，不过处理的方法我已经写在类库里面只要调用一下就好了，代码如下

```java
public class TestPlatformActivity extends Activity implements IWeiboHandler.Response {
	... 此处省略若干代码
	@Override
    public void onResponse(BaseResponse baseResponse) {
        mWbPlatform.handleWbResponse(baseResponse);
    }
	... 此处省略若干代码
}
```

- 4.然后需要在`onCreate()`和`onNewIntent()`方法中实现如下代码接受数据

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_platform);

    // 在这里实现是在Activity被销毁时接受数据，这里的this参数接受的是类型是IWeiboHandler.Response
    mWbPlatform.handleWeiboResponse(mActivity, savedInstanceState, this);
    ... 此处省略若干代码
}

@Override
protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    // Activity返回时被调用，这里的this参数接受的是类型是IWeiboHandler.Response
    mWbPlatform.onNewIntent(intent, this);
}
```

- 5.分享的API，这里分享有两种方式，一种是不需要登录的，也就是下面介绍的这几种方式，另外一种使用openApi来分享，需要用户授权，为什么要引入这种方式呢，因为微博分享图片大小有限制，不能大于32kb,就有点鸡肋啦，32kb的图片根本不够看。在下面的分享中很多字段分享到微博是看不到效果的，都是文字+网页链接，真的挺难看的，因为title,desc，bitmap这些字段都是显示在LinkCard上的，就是显示为卡片的样式，但是想能这样显示需要进行商业合作才可以,另外在textContent参数中的内容如果有网页链接，会自动隐藏替换为`网页链接`四个字是不会显示url的

```java
// 分享文字
mWbPlatform.shareText(mActivity, "test");
// 分享网页
mWbPlatform.shareWebpage(mActivity, "textContent", "title", "desc", bitmap, testWebUrl);
// 分享音乐
mWbPlatform.shareMusic(mActivity, "textContent", "title", "desc", bitmap, netMusicPath, netMusicPath, 10);
// 分享视频
mWbPlatform.shareVideo(mActivity, "textContent", "title", "desc", bitmap, netVideoPath, netVideoPath, 10);
// 分享声音
mWbPlatform.shareVoice(mActivity, "textContent", "title", "desc", bitmap, netMusicPath, netMusicPath, 10);
```
### 微博 openApi 分享
- 使用openApi分享需要先进行授权，也就是会弹起微博客户端授权界面点击登陆之后获得授权，当然，也已经封装好啦，只要调用分享的代码就可以啦。就直接贴代码啦

```java
// 分享图片bitmap
mWbPlatform.shareImage(mActivity, "text", bitmapThumb);
// 分享网络图片，这里是高级接口需要在微博后台申请的
mWbPlatform.shareNetImage(mActivity, "text", netImagePath);
// 分享GIF，这个分享出来是会有预览效果的
mWbPlatform.shareGif(mActivity, "text", localGifPath);
```

### 微博登录
- 微博登录授权需要在`onActivityResult()`接受授权的结果，，比起微信和QQ配置的地方太多啦，主要原因还是微信和QQ是有自己的Activity注册上啦，回头可以模仿微信和QQ的方式写个Activity接受数据，再返回给调用的Activity就不需要做如此多的配置啦。遗憾代码登录

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    mWbPlatform.onActivityResult(requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);
}



mWbPlatform.login(mActivity, new OnWbLoginListener() {
                    @Override
                    public void onSucceed(WbUserInfo info) {
                        log(info.toString());
                    }

                    @Override
                    public void onCancel() {
                        toast("取消");
                    }

                    @Override
                    public void onException(PlatformException e) {
                        e.getWbError().printStackTrace();
                    }
                });
```

## 结束
就这么多内容，这三个平台是目前接入最多的吧，提取这个Lib出来以后再做第三方登录分享就简单多啦，还有很多地方不足，API也不够简单，配置的地方太多，继续完善吧，加油