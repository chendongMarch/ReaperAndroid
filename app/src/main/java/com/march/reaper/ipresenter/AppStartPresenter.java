package com.march.reaper.ipresenter;

import com.march.bean.BeautyAlbum;
import com.march.reaper.base.mvp.presenter.BasePresenter;
import com.march.reaper.base.mvp.view.BaseView;
import com.march.reaper.common.API;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.imodel.BeautyAlbumResponse;
import com.march.reaper.imodel.UserInfo;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.utils.SPUtils;

import java.util.List;


/**
 * com.march.reaper.mvp.mPresenter.impl
 * Created by chendong on 16/7/19.
 * desc : AppStart界面的Presenter,
 */
public class AppStartPresenter
        extends BasePresenter<AppStartPresenter.AppStartView> {

    private UserInfo mUserInfo;

    public interface AppStartView extends BaseView {
        void loadViewImg(String url);
    }

    public AppStartPresenter() {
        this.mUserInfo = new UserInfo();
    }

    //使用deviceId注册
    public void registerByDeviceId(String name) {
        mUserInfo.registerByDeviceId(mView.getContext(), name);
    }

    //向服务器发开启记录
    public void recordStartApp() {
        mUserInfo.recordStartApp(mView.getContext());
    }

    //请求图片
    public void queryAppStartFlashImg() {
        final String appStartPhoto = SPUtils.get().getAppStartPhoto();
        if (appStartPhoto != null) {
            mView.loadViewImg(appStartPhoto);
        }
        QueryUtils.get().query(API.GET_LUCKY + "?limit=1", BeautyAlbumResponse.class,
                new QueryUtils.OnQueryOverListener<BeautyAlbumResponse>() {
                    @Override
                    public void queryOver(BeautyAlbumResponse rst) {
                        List<BeautyAlbum> data = rst.getData();
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
}
