package com.march.reaper.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.march.lib_base.common.Logger;
import com.march.lib_base.common.Toaster;
import com.march.reaper.base.ReaperApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

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

    private <T> Callback<T> createCallBack(final Class<T> cls, final OnQueryOverListener<T> listener) {
        return new Callback<T>() {
            @Override
            public T parseNetworkResponse(Response response) throws Exception {
                String string = response.body().string();
                Logger.e("数据到达" + string);
                return new Gson().fromJson(string, cls);
            }

            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
                Logger.e("请求出错" + e.toString());
                if (listener != null) {
                    listener.error(e);
                }
            }

            @Override
            public void onResponse(T response) {
                Logger.e("数据接受完毕,开始进行处理");
                if (listener != null) {
                    listener.queryOver(response);
                }
            }

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);
                Logger.e(progress + "");
            }
        };
    }


    public <T> void query(String url, final Class<T> cls, final OnQueryOverListener<T> listener) {
        Logger.e("get请求 -> " + url);
        if (checkCanNotDoRequest(listener)) {
            return;
        }
        OkHttpUtils.get().url(url).build().execute(createCallBack(cls, listener));
    }

    public <T> void get(String url, final Class<T> cls, final OnQueryOverListener<T> listener) {
        Logger.e("get请求 -> " + url);
        if (checkCanNotDoRequest(listener)) {
            return;
        }
        OkHttpUtils.get().url(url).build().execute(createCallBack(cls, listener));
    }

    public <T> void post(String url, final Class<T> cls, HashMap<String, String> params, final OnQueryOverListener<T> listener) {
        Logger.e("post请求 -> " + url);
        if (checkCanNotDoRequest(listener)) {
            return;
        }
        OkHttpUtils.post().url(url).params(params).build().execute(createCallBack(cls, listener));
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
