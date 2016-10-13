package com.march.reaper.iview.activity;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.ipresenter.impl.OffLineDataPresenterImpl;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 离线服务器数据,增量更新
 */
public class OffLineDataActivity extends BaseReaperActivity {

    @Bind(R.id.offline_albumdetail_process)
    ContentLoadingProgressBar mAlbumDetailProcessBar;
    @Bind(R.id.offline_recommend_process)
    ContentLoadingProgressBar mRecommendProcessBar;
    @Bind(R.id.offline_wholealbum_process)
    ContentLoadingProgressBar mWholeAlbumProcessBar;
    private OffLineDataPresenterImpl mOffLineDataPresenterImpl;

    @Override
    protected int getLayoutId() {
        return R.layout.off_line_data_activity;
    }

    @Override
    protected void destroyPresenter() {

    }

    @Override
    protected void onInitDatas() {
        super.onInitDatas();
        mOffLineDataPresenterImpl = new OffLineDataPresenterImpl();
    }


    @Override
    protected void onInitViews(Bundle save) {
        super.onInitViews(save);
        mTitleBarView.setText("我","数据离线",null);
    }

    @OnClick({R.id.offline_recommend, R.id.offline_wholealbum, R.id.offline_albumdetail})
    public void clickBtn(View v) {
        switch (v.getId()) {
            case R.id.offline_recommend:
                mOffLineDataPresenterImpl.offLineRecommend(mRecommendProcessBar);
                break;
            case R.id.offline_wholealbum:
                mOffLineDataPresenterImpl.offLineWholeAlbum(mWholeAlbumProcessBar);
                break;
            case R.id.offline_albumdetail:
                mOffLineDataPresenterImpl.offLineAlbumDetail(mAlbumDetailProcessBar);
                break;
        }
    }

    @Override
    protected String[] getPermission2Check() {
        return new String[0];
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }
}
