package com.march.reaper.mvp.contact;

import com.march.reaper.mvp.presenter.BasePresenter;
import com.march.reaper.mvp.ui.BaseView;

/**
 * com.march.reaper.mvp.contact
 * Created by chendong on 16/7/19.
 * desc :
 */
public interface RegisterContact {

    interface RegisterView extends BaseView {
        void hideGetCodeButton();
        void registerToMyServer();
        void registerSucceed();
        void userAlreadyExist();
        void registerFailed();
        void getCodeSucceed();
    }

    interface RegisterPresenter extends BasePresenter {
        void registerEventHandler();
        void registerToMyServer(String phone,String pwd);
    }
}
