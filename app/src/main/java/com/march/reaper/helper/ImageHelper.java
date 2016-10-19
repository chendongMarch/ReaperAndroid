package com.march.reaper.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.helper
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public class ImageHelper {
    public static void loadImg(Context context, String url, ImageView iv) {
        Glide.with(context).load(url)
                .crossFade().into(iv);
    }
}
