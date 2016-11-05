package com.march.reaper.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.provider.MediaStore;

import com.bumptech.glide.util.LruCache;
import com.jakewharton.disklrucache.DiskLruCache;
import com.march.lib.core.common.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.helper
 * CreateAt : 2016/10/15
 * Describe :
 *
 * @author chendong
 */

public class VideoThumbImageHelper {

    private static VideoThumbImageHelper mInst = new VideoThumbImageHelper();
    private DiskLruCache mDiskLruCache;

    private VideoThumbImageHelper() {

    }

    public static VideoThumbImageHelper get() {
        return mInst;
    }

    public void init(Context context) {
        mDiskLruCache = DiskLruCacheHelper.openDiskLruCache(context);
        Logger.e("VideoThumbImageHelper初始化结果 " + mDiskLruCache);
    }


    public Bitmap getThumbImage(String url) {
        String key = DiskLruCacheHelper.hashKeyForDisk(url);
        Bitmap bitmap = mThumbImageCache.get(key);
        if (bitmap == null) {
            Logger.e("全没有获取到。开始网络获取");
            bitmap = createVideoThumbnail(url);
            if (bitmap != null) {
                putThumbImage(url, bitmap);

                DiskLruCache.Editor edit = null;
                Logger.e("写入文件");
                try {
                    edit = mDiskLruCache.edit(key);
                    OutputStream outputStream = edit.newOutputStream(0);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Logger.e(mThumbImageCache.getMaxSize());
        Logger.e(bitmap.getByteCount());
        return bitmap;
    }

    private void putThumbImage(String url, Bitmap bitmap) {
        String key = DiskLruCacheHelper.hashKeyForDisk(url);
        mThumbImageCache.put(key, bitmap);
    }

    //两级缓存，文件+内存
    private static Bitmap createVideoThumbnail(String url) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            retriever.release();
        }
        if (bitmap != null)
            Logger.e(bitmap.getByteCount());
        return bitmap;
    }

    private LruCache<String, Bitmap> mThumbImageCache =
            new LruCache<String, Bitmap>(4 * 1024 * 1024) {
                @Override
                public Bitmap get(String key) {
                    Logger.e("开始内存获取");
                    //内存中取
                    Bitmap bitmap = super.get(key);
                    if (bitmap == null) {
                        Logger.e("内存中没有，开始文件获取");
                        //文件取
                        DiskLruCache.Snapshot snapshot = null;
                        try {
                            snapshot = mDiskLruCache.get(key);
                            if (snapshot != null)
                                bitmap = BitmapFactory.decodeStream(snapshot.getInputStream(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Logger.e("文件中获取结果 " + bitmap);
                        if (bitmap != null) {
                            mThumbImageCache.put(key, bitmap);
                        }
                    }
                    Logger.e("最终结果 " + bitmap);
                    return bitmap;
                }

                @Override
                public Bitmap put(String key, Bitmap item) {
                    Logger.e("写入内存");
                    return super.put(key, item);
                }

                @Override
                protected int getSize(Bitmap bitmap) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        return bitmap.getByteCount();
                    }
                    // Pre HC-MR1
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }

                @Override
                protected void onItemEvicted(String key, Bitmap item) {
                    Logger.e("内存超出");
                    super.onItemEvicted(key, item);
                    mThumbImageCache.remove(key);
                    if (item != null && !item.isRecycled()) {
                        item.recycle();
                    }
                }
            };
}
