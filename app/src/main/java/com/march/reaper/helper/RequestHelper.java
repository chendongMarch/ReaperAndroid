package com.march.reaper.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.march.lib.core.common.Logger;
import com.march.lib.core.common.Toaster;
import com.march.reaper.base.ReaperApplication;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by march on 16/7/1.
 * 封装的请求类,从网络请求数据
 */
public class RequestHelper {

    private static RequestHelper mInst;

    private RequestHelper() {
    }

    public static RequestHelper get() {
        if (mInst == null) {
            synchronized (RequestHelper.class) {
                if (mInst == null) {
                    mInst = new RequestHelper();
                }
            }
        }
        return mInst;
    }


    public interface OnQueryOverListener<T> {
        void queryOver(T rst);

        void error(Exception e);
    }

    /**
     * 2.判断网络是否连接，有一种网络连接就返回true,一般使用该方法来判断网络
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 3.判断当前的Wifi网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return networkInfo != null && networkInfo.isConnected();
        } else {
            return isConnected(connMgr, ConnectivityManager.TYPE_WIFI);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static boolean isConnected(@NonNull ConnectivityManager connMgr, int type) {
        Network[] networks = connMgr.getAllNetworks();
        NetworkInfo networkInfo;
        for (Network mNetwork : networks) {
            networkInfo = connMgr.getNetworkInfo(mNetwork);
            if (networkInfo != null && networkInfo.getType() == type && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkCanNotDoRequest(OnQueryOverListener listener) {
        if (!isNetworkConnected(ReaperApplication.get())) {
            Toaster.get().show(ReaperApplication.get(), "网络不给力哦");
            if (listener != null)
                listener.error(new RuntimeException("网络不给力"));
            return true;
        }
        return false;
    }
}
