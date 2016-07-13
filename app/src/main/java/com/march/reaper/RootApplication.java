package com.march.reaper;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.march.quickrvlibs.RvQuick;
import com.march.quickrvlibs.helper.QuickLoad;
import com.march.reaper.common.DaoHelper;

import java.io.File;

/**
 * Created by march on 16/6/6.
 * application
 */
public class RootApplication extends Application {

    private static RootApplication mInst;

    @Override
    public void onCreate() {
        super.onCreate();
        mInst = this;
        initRvQuick();
        DaoHelper.get().setupDatabase(this);
    }

    public static RootApplication get() {
        return mInst;
    }


    //    初始化QuickAdapter
    private void initRvQuick() {
        RvQuick.init(new QuickLoad() {
            @Override
            public void load(Context context, String url, ImageView view) {
                Glide.with(context).load(url).centerCrop().crossFade().into(view);
            }

            @Override
            public void loadSizeImg(Context context, String url, ImageView view, int w, int h, int placeHolder) {
                Glide.with(context).load(url).centerCrop().crossFade().into(view);
            }
        });
    }


    public File getDownloadDir() {
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "/reaperDownload");
        if (!downloadDir.exists())
            downloadDir.mkdirs();
        return downloadDir;
    }


}
