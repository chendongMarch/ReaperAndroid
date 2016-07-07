package com.march.reaper.mvp.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.march.reaper.R;
import com.march.reaper.ReaperActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.utils.Lg;
import com.zhy.http.okhttp.utils.L;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.OnClick;
import me.leolin.shortcutbadger.ShortcutBadger;

public class AppStartActivity extends ReaperActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.start_app_activity;
    }


    @Override
    protected void finalOperate() {
        super.finalOperate();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(AppStartActivity.this, HomePageActivity.class));
            }
        }, 1500);
    }

    int count = 0;

    @OnClick({R.id.btn_shortcut_count, R.id.btn_remove})
    public void clickBtn(View view) {
        Lg.e("click " + Build.MANUFACTURER);
        switch (view.getId()) {
            case R.id.btn_shortcut_count:
                count++;
                if (Build.MANUFACTURER.equals("Xiaomi")) {
                    xiaomi(this,count);
                } else {
                    ShortcutBadger.applyCount(getApplicationContext(), count);
                }

                break;
            case R.id.btn_remove:
                if (Build.MANUFACTURER.equals("Xiaomi")) {
//                    xiaomi(this,count);
                } else {
                    ShortcutBadger.applyCount(getApplicationContext(), 0);
                }
                break;
        }
    }


    private void xiaomi(Context context, int count) {
        boolean isUpMIUIV6 = true;
        NotificationManager mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("title").setContentText("content").setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = builder.build();
        try {
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, count);
        } catch (Exception e) {
            e.printStackTrace();
            // miui 6之前的版本
            isUpMIUIV6 = false;
            Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
            localIntent.putExtra("android.intent.extra.update_application_component_name",
                    context.getPackageName() + "/" + getLauncherClassName(context));
            localIntent.putExtra("android.intent.extra.update_application_message_text", String.valueOf(count == 0 ? "" : count));
            context.sendBroadcast(localIntent);
        } finally {
            if (notification != null && isUpMIUIV6) {
                //miui6以上版本需要使用通知发送
                mNotificationManager.notify(101010, notification);
            }
        }
    }



    private static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ResolveInfo info = packageManager
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }
        return info.activityInfo.name;
    }


    private void sendToSony(String number) {
        boolean isShow = true;
        if ("0".equals(number)) {
            isShow = false;
        }
        Intent localIntent = new Intent();
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow);//是否显示
        localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", getLauncherClassName(this));//启动页
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", number);//数字
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", getPackageName());//包名
        sendBroadcast(localIntent);
    }

    private void sendToSamsumg(String number) {
        Intent localIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        localIntent.putExtra("badge_count", number);//数字
        localIntent.putExtra("badge_count_package_name", getPackageName());//包名
        localIntent.putExtra("badge_count_class_name", getLauncherClassName(this)); //启动页
        sendBroadcast(localIntent);
    }

}
