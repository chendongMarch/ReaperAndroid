package com.march.reaper.helper;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.march.reaper.BuildConfig;
import com.march.reaper.common.API;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.helper
 * CreateAt : 2016/11/19
 * Describe :
 *
 * @author chendong
 */
public class RetrofitHelper {

    public static final int mConnectedTime = 5;
    public static final int mReadTime = 20;

    public static Retrofit init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .connectTimeout(mConnectedTime, TimeUnit.SECONDS)
                .readTimeout(mReadTime, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        if (BuildConfig.DEBUG)
            builder.addNetworkInterceptor(new StethoInterceptor());

        return new Retrofit.Builder()
                .baseUrl(API.BASE)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
