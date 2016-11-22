package com.march.reaper.helper;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.march.lib.core.common.Logger;
import com.march.reaper.widget.GlideRoundTransform;
import com.march.reaper.widget.LeProgressView;

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

    public static void loadImgProgress(Context context, String url, ImageView iv, final View loadingView) {
        Glide.with(context).load(url).into(new ImageViewTarget<GlideDrawable>(iv) {
            @Override
            protected void setResource(GlideDrawable resource) {
                view.setImageDrawable(resource);
                Logger.e("加载完毕");
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
}
