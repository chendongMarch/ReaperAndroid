package com.march.reaper.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * com.march.reaper.utils
 * Created by chendong on 16/7/19.
 * desc : 虽然有glide,但是在这里统一管理图片加载
 */
public final class ImgLoadUtils {
    public static void loadImg(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).crossFade().into(iv);
    }
}
