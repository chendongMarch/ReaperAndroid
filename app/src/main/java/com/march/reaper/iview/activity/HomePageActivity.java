package com.march.reaper.iview.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.march.reaper.R;
import com.march.reaper.mvp.ui.MultiFragmentActivity;
import com.march.reaper.iview.fragment.AlbumQueryFragment;
import com.march.reaper.iview.fragment.MineFragment;
import com.march.reaper.iview.fragment.RecommendFragment;
import com.march.reaper.iview.fragment.SearchFragment;

import java.util.List;

import butterknife.Bind;
import buttrknife.OnClick;

/**
 * 主页分为四个fragment ,第一个fragment是viewpager(包含多个fragment)
 */
public class HomePageActivity extends MultiFragmentActivity {

    @Bind({R.id.home_recommend, R.id.home_album, R.id.home_search, R.id.home_mine})
    List<TextView> mBotTabsTv;
//    private int mShowItem = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.home_activity_page;
    }

    @Override
    protected void destroyPresenter() {

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
                fragment = new RecommendFragment();
                break;
            case 1:
                fragment = AlbumQueryFragment.newInst();
                break;
            case 2:
                fragment = new SearchFragment();
                break;
            case 3:
                fragment = new MineFragment();
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
        selectItemFragment(tag, false);
    }
}
