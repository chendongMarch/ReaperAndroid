package com.march.reaper.utils;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by march on 16/7/1.
 * 封装的请求类
 */
public class QueryUtils {


    private static QueryUtils mInst;

    private QueryUtils() {
    }

    public static QueryUtils get() {
        if (mInst == null) {
            synchronized (QueryUtils.class) {
                if (mInst == null) {
                    mInst = new QueryUtils();
                }
            }
        }
        return mInst;
    }


    public interface OnQueryOverListener<T>{
        void queryOver(T rst);
    }

    public <T> void query(String url, final Class<T> cls, final OnQueryOverListener<T> listener) {
        OkHttpUtils.get().url(url).build().execute(new Callback<T>() {
            @Override
            public T parseNetworkResponse(Response response) throws Exception {
                Lg.e("数据到达");
                return new Gson().fromJson(response.body().string(), cls);
            }

            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
                Lg.e("请求出错" + e.toString());
            }

            @Override
            public void onResponse(T response) {
                Lg.e("数据接受完毕,开始进行处理");
                if (listener != null) {
                    listener.queryOver(response);
                }
            }

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);
                Lg.e(progress+"");
            }
        });
    }
}
