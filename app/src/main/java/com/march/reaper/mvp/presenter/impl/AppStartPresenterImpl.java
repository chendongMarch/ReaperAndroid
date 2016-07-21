package com.march.reaper.mvp.presenter.impl;

import android.content.Intent;

import com.march.bean.WholeAlbumItem;
import com.march.reaper.common.API;
import com.march.reaper.mvp.model.WholeAlbumResponse;
import com.march.reaper.mvp.presenter.BaseActivityPresenter;
import com.march.reaper.mvp.presenter.WithViewTypePresenter;
import com.march.reaper.mvp.ui.BaseView;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.utils.SPUtils;

import java.util.List;

/**
 * com.march.reaper.mvp.presenter.impl
 * Created by chendong on 16/7/19.
 * desc : AppSrart界面的Presenter,
 */
public class AppStartPresenterImpl
        extends BaseActivityPresenter<AppStartPresenterImpl.AppStartView> {

    public AppStartPresenterImpl(RootActivity mContext) {
        super(mContext);
    }

    public interface AppStartView extends BaseView {
        void loadViewImg(String url);
    }

    //请求图片
    public void queryAppStartFlashImg() {
        final String appStartPhoto = SPUtils.get().getAppStartPhoto();
        if (appStartPhoto != null) {
            mView.loadViewImg(appStartPhoto);
        }
        QueryUtils.get().query(API.GET_LUCKY + "?limit=1", WholeAlbumResponse.class,
                new QueryUtils.OnQueryOverListener<WholeAlbumResponse>() {
                    @Override
                    public void queryOver(WholeAlbumResponse rst) {
                        List<WholeAlbumItem> data = rst.getData();
                        String album_cover = data.get(0).getAlbum_cover();
                        if (appStartPhoto == null) {
                            mView.loadViewImg(album_cover);
                        }
                        SPUtils.get().putAppStartPhoto(album_cover);
                    }

                    @Override
                    public void error(Exception e) {

                    }
                });
    }

    public void setIntent(Intent intent) {

    }
}
