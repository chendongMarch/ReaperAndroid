package com.march.reaper.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Project  : CommonLib
 * Package  : com.march.baselib
 * CreateAt : 16/8/13
 * Describe : 唤醒系统分享
 *
 * @author chendong
 */
public class ShareHelper {
    private static ShareHelper mSysShare;
    private Context context;

    private ShareHelper(Context context) {
        this.context = context;
    }

    /**
     * 获取单例
     * @param context 上下文
     * @return ShareHelper
     */
    public static ShareHelper get(Context context) {
        if (mSysShare == null) {
            synchronized (ShareHelper.class) {
                mSysShare = new ShareHelper(context);
            }
        }
        return mSysShare;
    }

    /**
     * 分享文字
     *
     * @param title   文字标题
     * @param content 文字内容
     */
    public void shareText(String title, String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.putExtra(Intent.EXTRA_TITLE, title);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    /**
     * 分享单张图片
     *
     * @param path 图片的路径
     */
    public void shareSingleImage(String path) {
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(path));
//        Log.d("share", "uri:" + imageUri);  //输出：file:///storage/emulated/0/test.jpg
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    /**
     * 分享多张图片
     *
     * @param paths 路径的集合
     */
    public void shareMultipleImage(List<String> paths) {
        ArrayList<Uri> uriList = new ArrayList<>();
        for (String path : paths) {
            uriList.add(Uri.fromFile(new File(path)));
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


}
