package com.march.reaper.iview.activity;

import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.TextView;

import com.march.reaper.R;
import com.march.reaper.base.activity.MultiFragmentActivity;
import com.march.reaper.base.mvp.life.PresenterLoader;
import com.march.reaper.iview.fragment.BeautyFragment;
import com.march.reaper.iview.fragment.FunnyFragment;
import com.march.reaper.iview.fragment.MineFragment;
import com.march.reaper.iview.fragment.VideoFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 主页分为四个fragment ,第一个fragment是viewpager(包含多个fragment)
 */
public class HomePageActivity extends MultiFragmentActivity {

    @Bind({R.id.home_recommend, R.id.home_album, R.id.home_search, R.id.home_mine})
    List<TextView> mBotTabsTv;

    @Override
    protected int getLayoutId() {
        return R.layout.home_activity_page;
    }

    @Override
    protected boolean whenShowSameFragment(int showItem) {
        return false;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.home_container;
    }

    @Override
    protected Fragment makeFragment(int showItem) {
        Fragment fragment = null;
        switch (showItem) {
            case 0:
                fragment = VideoFragment.newInst();
                break;
            case 1:
                fragment = FunnyFragment.newInst();
                break;
            case 2:
                fragment = BeautyFragment.newInst();
                break;
            case 3:
                fragment = MineFragment.newInst();
                break;
        }
        return fragment;
    }

    @Override
    protected void syncSelectState(int selectImage) {
        for (int i = 0; i < mBotTabsTv.size(); i++) {
            mBotTabsTv.get(i).setSelected(selectImage == i);
        }
    }

    @OnClick({R.id.home_recommend, R.id.home_album, R.id.home_search, R.id.home_mine})
    public void click(View v) {
        int tag = Integer.parseInt(v.getTag().toString());
        showFragment(tag);
    }

    @Override
    protected String[] getPermission2Check() {
        return new String[0];
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    @Override
    protected PresenterLoader createPresenterLoader() {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }
}
