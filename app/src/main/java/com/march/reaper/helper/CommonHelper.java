package com.march.reaper.helper;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.march.lib.core.common.Toaster;
import com.march.reaper.iview.dialog.LoadingDialog;

import java.io.IOException;
import java.util.Random;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.helper
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public class CommonHelper {
    public static int randomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(150), random.nextInt(150), random.nextInt(150));
    }

    public static int getVersionCode(Context context) {

        String pkName = context.getPackageName();
        try {
            String versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;

            int versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
