package com.march.reaper.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.march.quickrvlibs.RvQuick;
import com.march.quickrvlibs.helper.QuickLoad;
import com.march.reaper.R;
import com.march.reaper.common.DaoHelper;
import com.march.reaper.helper.ImageHelper;
import com.march.reaper.helper.Logger;
import com.march.reaper.helper.Toaster;

import java.io.File;


/**
 * Created by march on 16/6/6.
 * application
 */
public class ReaperApplication extends Application {

    private static ReaperApplication mInst;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e("app create");
        mInst = this;
        initRvQuick();
        DaoHelper.get().setupDatabase(this);
        Toaster.get().initToastBulder(new Toaster.ToastBuilder() {
            @Override
            public Toast buildToast(Toast toast, String msg) {
                View inflate = LayoutInflater.from(mInst).inflate(R.layout.common_custom_toast, null);
                TextView textView = (TextView) inflate.findViewById(R.id.toast_tv);
                textView.setText(msg);
                toast.setView(inflate);
                return toast;
            }

            @Override
            public void setText(Toast toast, String msg) {
                TextView tv = (TextView) toast.getView().findViewById(R.id.toast_tv);
                tv.setText(msg);
            }
        });
    }


    public static ReaperApplication get() {
        return mInst;
    }


    // 初始化QuickAdapter
    private void initRvQuick() {
        RvQuick.init(new QuickLoad() {
            @Override
            public void loadImg(Context context, String url, ImageView view) {
                ImageHelper.loadImg(context, url, view);
            }

            @Override
            public void loadSizeImg(Context context, String url, ImageView view, int w, int h, int placeHolder) {
                super.loadSizeImg(context, url, view, w, h, placeHolder);
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
