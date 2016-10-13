package com.march.reaper.ipresenter.impl;

import android.os.AsyncTask;
import android.support.v4.widget.ContentLoadingProgressBar;

import com.march.reaper.common.API;
import com.march.reaper.common.DaoHelper;
import com.march.reaper.common.DbHelper;
import com.march.reaper.helper.Logger;
import com.march.reaper.imodel.AlbumDetailResponse;
import com.march.reaper.imodel.BaseResponse;
import com.march.reaper.imodel.RecommendAlbumResponse;
import com.march.reaper.imodel.WholeAlbumResponse;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.utils.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by march on 16/7/1.
 * 离线数据的操作
 */
public class OffLineDataPresenterImpl {

    private SimpleDateFormat mTimeStampFormat;

    public OffLineDataPresenterImpl() {

        mTimeStampFormat = new SimpleDateFormat("yyyyMMddhhmm", Locale.CHINA);

        DaoHelper.get().getAlbumDetailDao().deleteAll();

        DaoHelper.get().getRecommendAlbumItemDao().deleteAll();

        DaoHelper.get().getWholeAlbumItemDao().deleteAll();
    }

    //将数据保存到数据库
    private void saveData2GreenDao(final BaseResponse response) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Logger.e("开始存储,数据大小 = " + response.getData().size());
                DaoHelper.get().getDao(response.getClass()).insertOrReplaceInTx(response.getData());
                Logger.e("存储完毕,当前数据库大小 = " + DbHelper.get().queryCount(response.getClass()));
                syncLocalTimeStamp(response.getClass());
                return null;
            }
        }.execute();
    }

    //同步本地时间戳
    private void syncLocalTimeStamp(Class cls) {
        SPUtils.get().putTimeStamp(cls, mTimeStampFormat.format(new Date()));
    }

    //请求数据
    private <T extends BaseResponse> void queryData(String url, final Class<T> cls) {
        //取出时间戳,拼凑url
        StringBuilder sb = new StringBuilder(url).append("?time=").append("000000000000");
//        StringBuilder sb = new StringBuilder(url).append("?time=").append(SPUtils.get().getTimeStamp(cls));
        QueryUtils.get().query(sb.toString(), cls, new QueryUtils.OnQueryOverListener<T>() {
            @Override
            public void queryOver(T rst) {
                saveData2GreenDao(rst);
                syncLocalTimeStamp(cls);
            }

            @Override
            public void error(Exception e) {

            }
        });
    }

    public void offLineRecommend(ContentLoadingProgressBar mProcessBar) {
        queryData(API.GET_OFFLINE_RECOMMEND, RecommendAlbumResponse.class);
    }

    public void offLineWholeAlbum(ContentLoadingProgressBar mProcessBar) {
        queryData(API.GET_OFFLINE_WHOLE, WholeAlbumResponse.class);
    }

    public void offLineAlbumDetail(ContentLoadingProgressBar mProcessBar) {
        queryData(API.GET_OFFLINE_DETAIL, AlbumDetailResponse.class);
    }
}
