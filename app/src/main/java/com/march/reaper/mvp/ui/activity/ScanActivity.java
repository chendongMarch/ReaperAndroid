package com.march.reaper.mvp.ui.activity;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.march.quickrvlibs.RvQuickAdapter;
import com.march.quickrvlibs.RvViewHolder;
import com.march.reaper.R;
import com.march.reaper.ReaperActivity;
import com.march.reaper.ReaperApplication;
import com.march.reaper.mvp.model.AlbumItemResponse;
import com.march.reaper.utils.DisplayUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;

public class ScanActivity extends ReaperActivity {

    @Bind(R.id.album_recycler)
    RecyclerView mAlbumRv;

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity_scan;
    }

    private void showAlbums(List<AlbumItemResponse.AlbumItem> items) {
        RvQuickAdapter<AlbumItemResponse.AlbumItem> adapter =
                new RvQuickAdapter<AlbumItemResponse.AlbumItem>(self, items, R.layout.test_item_scan) {
                    @Override
                    public void bindData4View(RvViewHolder holder, AlbumItemResponse.AlbumItem data, int pos, int type) {
                        ImageView view = holder.getView(R.id.item_scan_iv);
                        view.getLayoutParams().height = DisplayUtils.getScreenWidth();
                        holder.setImg(self, R.id.item_scan_iv, data.getPic_src())
                                .setText(R.id.item_scan_tv, data.getPic_desc());
                    }
                };
        mAlbumRv.setLayoutManager(new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false));
        mAlbumRv.setAdapter(adapter);
    }

    @Override
    protected void finalOperate() {
        super.finalOperate();

        OkHttpUtils.get().url("http://192.168.1.101:3000/findalbum?offset=100")
                .build().execute(new Callback<String>() {
            @Override
            public String parseNetworkResponse(final Response response) throws Exception {

                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(final String response) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        AlbumItemResponse albumItemResponse =
                                new Gson().fromJson(response, AlbumItemResponse.class);
                        showAlbums(albumItemResponse.getData());
                    }
                });
            }
        });
    }
}
