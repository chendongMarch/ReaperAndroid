package com.march.reaper.base;

import android.app.Application;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.antfortune.freeline.FreelineCore;
import com.march.lib.core.common.Logger;
import com.march.lib.core.common.Toaster;
import com.march.lib.platform.Platform;
import com.march.reaper.R;
import com.march.reaper.common.API;
import com.march.reaper.common.DaoHelper;
import com.march.reaper.helper.VideoThumbImageHelper;

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
        DaoHelper.get().setupDatabase(this);

        Toaster.get().initToastBuilder(new Toaster.ToastBuilder() {
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

        FreelineCore.init(this);
        API.init();
        VideoThumbImageHelper.get().init(mInst);
        Platform.newInst(this);
        Platform.getInst().initWx(this, "wx87fa4edcc9bb3e84");
        Platform.getInst().initQQ(this, "1105571460");
        Platform.getInst().initWb(this, "3699350615");
    }


    public static ReaperApplication get() {
        return mInst;
    }


    public File getDownloadDir() {
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "/reaper/download");
        if (!downloadDir.exists())
            downloadDir.mkdirs();
        return downloadDir;
    }

    public File getThumbnailDir() {
        File thumbDir = new File(Environment.getExternalStorageDirectory(), "/reaper/.thumb");
        if (!thumbDir.exists())
            thumbDir.mkdirs();
        return thumbDir;
    }


}
