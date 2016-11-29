package com.march.reaper.helper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.march.lib.core.common.Logger;
import com.march.lib.core.common.Toaster;
import com.march.lib.support.helper.FileHelper;
import com.march.lib.support.helper.ShareHelper;
import com.march.lib.view.LeProgressView;
import com.march.reaper.base.ReaperApplication;
import com.march.reaper.widget.GlideRoundTransform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.helper
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public class ImageHelper {


    public static void loadRoundImg(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).transform(new GlideRoundTransform(context, 50)).crossFade().into(iv);
    }

    public static void loadImg(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).thumbnail(0.1f).crossFade().into(iv);
    }

    public static void loadImg(Context context, String url, int w, int h, ImageView iv) {
        Glide.with(context).load(url).override(w, h).crossFade().into(iv);
    }

    public static void loadImgProgress(Activity context, String url, ImageView iv, final View loadingView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && context.isDestroyed()) {
            Logger.e("load image after activity on destroy");
            return;
        }
        Glide.with(context).load(url).into(new ImageViewTarget<GlideDrawable>(iv) {
            @Override
            protected void setResource(GlideDrawable resource) {
                view.setImageDrawable(resource);
                if (loadingView != null) {
                    ((LeProgressView) loadingView).stopLoading(new Runnable() {
                        @Override
                        public void run() {
                            loadingView.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

    }

    public interface OnDownloadOverHandler {
        void onSuccess(Bitmap bitmap);
    }

    // 设置壁纸，锁屏壁纸和桌面壁纸
    public static void setWallPaper(final Context context, final boolean isScreen, final boolean isLockScreen, String url, final Runnable endRunnable) {
        downloadPic(context, url, new OnDownloadOverHandler() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                WallpaperManager wm = WallpaperManager.getInstance(context);
                try {
                    if (isScreen) {
                        wm.setBitmap(bitmap);
                        Toaster.get().show(context, "桌面壁纸设置成功～");
                    }
                    if (isLockScreen) {
                        SetLockWallPaper(context, bitmap);
                        Toaster.get().show(context, "锁屏壁纸设置成功～");
                    }
                } catch (IOException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                    if (e instanceof IOException) {
                        Toaster.get().show(context, "桌面壁纸设置失败");
                    } else {
                        Toaster.get().show(context, "锁屏壁纸设置失败");
                    }
                    e.printStackTrace();
                } finally {
                    if (endRunnable != null) {
                        endRunnable.run();
                    }
                }

            }
        });
    }

    private static void SetLockWallPaper(Context context, Bitmap bitmap) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        WallpaperManager mWallManager = WallpaperManager.getInstance(context);
        Class class1 = mWallManager.getClass();//获取类名
        Method setWallPaperMethod = class1.getMethod("setBitmapToLockWallpaper", Bitmap.class);//获取设置锁屏壁纸的函数
        setWallPaperMethod.invoke(mWallManager, bitmap);//调用锁屏壁纸的函数，并指定壁纸的路径imageFilesPath
    }

    //图片保存到本地和分享
    public static void saveToLocal(final Context context, String url, final boolean isShare, final Runnable endRunnable) {
        downloadPic(context, url, new OnDownloadOverHandler() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                File saveDir;
                String fileName = "reaper_" + System.currentTimeMillis() + ".jpg";
                try {
                    if (isShare) {
                        saveDir = ReaperApplication.get().getThumbnailDir();
                    } else {
                        saveDir = ReaperApplication.get().getDownloadDir();
                    }
                    File file = new File(saveDir, fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
                    if (isShare) {
                        ShareHelper.get(context).shareSingleImage(file.getAbsolutePath());
                    } else {
                        Toaster.get().show(context, "图片下载完毕,保存到 " + file.getAbsolutePath());
                        FileHelper.insertImageToMediaStore(context, file.getAbsolutePath(), System.currentTimeMillis());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (endRunnable != null) {
                        endRunnable.run();
                    }
                }
            }
        });
    }

    private static void downloadPic(Context context, String url, final OnDownloadOverHandler handler) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource,
                                        GlideAnimation<? super Bitmap> glideAnimation) {
                handler.onSuccess(resource);
            }
        });
    }
}
