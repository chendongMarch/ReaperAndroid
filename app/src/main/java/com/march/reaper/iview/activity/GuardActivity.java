package com.march.reaper.iview.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.march.lib.core.common.Logger;
import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class GuardActivity extends BaseReaperActivity {

    @Bind(R.id.et_guard)
    EditText mGuardEt;

    @Override
    protected int getLayoutId() {
        return R.layout.guard_activity;
    }


    @Override
    public void onInitViews(View view, Bundle saveData) {
        super.onInitViews(view, saveData);
        final long time = System.currentTimeMillis();
        Logger.e("开始请求图片" + "   " + time);

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

    @Override
    public void onInitEvents() {
        super.onInitEvents();
        mGuardEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("91109123"))
                    startAppStart();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void startAppStart() {
        startActivity(AppStartActivity.class);
        animFinish();
    }

    @OnClick(R.id.btn_check)
    public void clickBtn(View view) {
        if (getText(mGuardEt).equals("91109123")) {
            startAppStart();
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }
}
