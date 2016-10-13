package com.march.reaper.ipresenter.impl;

import android.content.Intent;

import com.march.bean.WholeAlbumItem;
import com.march.reaper.common.API;
import com.march.reaper.imodel.BaseResponse;
import com.march.reaper.imodel.WholeAlbumResponse;
import com.march.reaper.ipresenter.BaseActivityPresenter;
import com.march.reaper.iview.BaseView;
import com.march.reaper.iview.RootActivity;
import com.march.reaper.utils.AppUtils;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.utils.SPUtils;

import java.util.HashMap;
import java.util.List;

/**
 * com.march.reaper.mvp.presenter.impl
 * Created by chendong on 16/7/19.
 * desc : AppSrart界面的Presenter,
 */
public class NewAppStartPresenterImpl
        extends BaseActivityPresenter<NewAppStartPresenterImpl.AppStartView> {

    public NewAppStartPresenterImpl(RootActivity mContext) {
        super(mContext);
    }

    private void autoPostInfo(String url, HashMap<String, String> params) {
        QueryUtils.get().post(url, BaseResponse.class, params, null);
    }

    //使用deviceid注册
    public void registerByDeviceId(String name) {
        authority();
        SPUtils.get().putUserName(name);
        HashMap<String, String> userInfo = AppUtils.getUserInfo(mContext);
        userInfo.put("userName", name);
        autoPostInfo(API.POST_AUTO_REGISTER, userInfo);
    }

    //向服务器发开启记录
    public void recordStartApp() {
        HashMap<String, String> userInfo = AppUtils.getUserInfo(mContext);
        userInfo.put("userName", SPUtils.get().getUserName());
        autoPostInfo(API.POST_AUTO_Record, userInfo);
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
