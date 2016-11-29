package com.march.reaper.helper;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.march.lib.core.common.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

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

    public static void testImageSize() {
        final long time = System.currentTimeMillis();
        String url = "http://mm.howkuai.com/wp-content/uploads/2016a/08/17/04.jpg";

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        Request request = new Request.Builder().get().url(url).build();
        builder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(inputStream, null, options);
                Logger.e((System.currentTimeMillis() - time) + "   " + options.outWidth + "   " + options.outHeight + "  " + options.outMimeType);
            }
        });
    }

}
