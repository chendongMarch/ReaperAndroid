package com.march.reaper.ipresenter;

import com.march.reaper.imodel.bean.BeautyAlbum;
import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.lib.core.mvp.view.BaseView;
import com.march.reaper.common.API;
import com.march.reaper.common.EMptyRequestCallback;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.helper.SharePreferenceHelper;
import com.march.reaper.imodel.BeautyAlbumResponse;
import com.march.reaper.imodel.UserInfo;

import java.util.List;

import io.reactivex.Flowable;


/**
 * com.march.reaper.mvp.mPresenter.impl
 * Created by chendong on 16/7/19.
 * desc : AppStart界面的Presenter,
 */
public class AppStartPresenter
        extends BasePresenter<AppStartPresenter.AppStartView> {


    public interface AppStartView extends BaseView {
        void loadViewImg(String url);
    }

    public AppStartPresenter() {
    }

    //使用deviceId注册
    public void registerByDeviceId(String name) {
        UserInfo.registerByDeviceId(name, new EMptyRequestCallback<>());
    }

    //向服务器发开启记录
    public void recordStartApp() {
        UserInfo.recordStartApp(new EMptyRequestCallback<>());
    }

    //请求图片
    public void queryAppStartFlashImg() {
        final String appStartPhoto = SharePreferenceHelper.get().getAppStartPhoto();
        if (appStartPhoto != null) {
            mView.loadViewImg(appStartPhoto);
        }

        RequestCallback<BeautyAlbumResponse> callback = new RequestCallback<BeautyAlbumResponse>() {
            @Override
            public void onSucceed(BeautyAlbumResponse data) {
                List<BeautyAlbum> list = data.getData();
                String album_cover = list.get(0).getAlbum_cover();
                if (appStartPhoto == null) {
                    mView.loadViewImg(album_cover);
                }
                SharePreferenceHelper.get().putAppStartPhoto(album_cover);
            }

            @Override
            public void onError(Throwable e) {

            }
        };
        Flowable<BeautyAlbumResponse> luckAlbum = API.api().getLuckAlbum(1);
        API.enqueue(luckAlbum, callback);
    }
}
