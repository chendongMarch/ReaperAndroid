package com.march.reaper;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.reaper.helper.VideoThumbImageHelper;
import com.march.reaper.widget.JCVideoPlayerStandard;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class TestActivity extends BaseReaperActivity {


    @Bind(R.id.custom_videoplayer_standard)
    JCVideoPlayerStandard jcVideoPlayerStandard;
    @Bind(R.id.iv)
    ImageView iv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void onStartWorks() {
        super.onStartWorks();
//        jcVideoPlayerStandard.setUp("http://v2.pstatp.com/p360/52/5555540270.mp4"
//                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");

        String absolutePath = new File(Environment.getExternalStorageDirectory(), "123.mp").getAbsolutePath();
        jcVideoPlayerStandard.setUp(absolutePath
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");

        Bitmap thumbImage = VideoThumbImageHelper.get().getThumbImage("http://v2.pstatp.com/p360/52/5555540270.mp4");
        iv.setImageBitmap(thumbImage);
    }

    @OnClick({R.id.btn})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Bitmap thumbImage = VideoThumbImageHelper.get().getThumbImage("http://v2.pstatp.com/p360/52/5555540270.mp4");
                iv.setImageBitmap(thumbImage);
                break;
        }
    }

    @Override
    protected String[] getPermission2Check() {
        return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
