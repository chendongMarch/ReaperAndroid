package com.march.reaper.utils;

import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.march.reaper.R;
import com.march.reaper.RootApplication;

import java.util.zip.Inflater;

/**
 * Created by march on 16/7/8.
 * Toast
 */
public class To {

    public static void show(String msg) {
        Toast toast = new Toast(RootApplication.get());
        toast.setGravity(Gravity.TOP, 0, DisplayUtils.dp2px(RootApplication.get(), 50));
        View inflate = LayoutInflater.from(RootApplication.get()).inflate(R.layout.toast_custom, null);
        TextView tv = (TextView) inflate.findViewById(R.id.toast_tv);
        tv.setText(msg);
        toast.setView(inflate);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
