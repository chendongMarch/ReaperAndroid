package com.march.reaper.mvp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.march.quickrvlibs.RvQuickAdapter;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.helper.RvConvertor;
import com.march.quickrvlibs.model.RvQuickModel;
import com.march.reaper.R;
import com.march.reaper.ReaperActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.mvp.presenter.impl.HomePresenterImpl;

import butterknife.Bind;

public class HomeActivity extends ReaperActivity {

    //声明相关变量
    @Bind(R.id.home_toolbar)
    Toolbar mToolBar;
    @Bind(R.id.home_drawer)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.home_left_menu)
    RecyclerView mLeftMenuRv;
    private HomePresenterImpl mHomePresenterImpl;

    @Bind(R.id.album_rv1)
    RecyclerView rv;
    @Override
    protected int getLayoutId() {
        return R.layout.test_activity_home;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        self = this;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        buildToolBarAndDrawer();
        buildDrawerMenu();
    }

    //    创建左边菜单
    private void buildDrawerMenu() {
        mLeftMenuRv.setLayoutManager(new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false));
        mLeftMenuRv.setAdapter(new RvQuickAdapter<RvQuickModel>(self, RvConvertor.convert(Constant.mMenuItem), R.layout.test_item_left_menu) {
            @Override
            public void bindData4View(RvViewHolder holder, RvQuickModel data, int pos, int type) {
                holder.setText(R.id.item_menu_name, data.<String>get());
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new RvQuickAdapter<RvQuickModel>(self, RvConvertor.convert(Constant.mMenuItemTest), R.layout.test_item_left_menu) {
            @Override
            public void bindData4View(RvViewHolder holder, RvQuickModel data, int pos, int type) {
                holder.setText(R.id.item_menu_name, data.<String>get());
            }
        });


    }

    //    初始化ToolBar和Drawer
    private void buildToolBarAndDrawer() {
        mToolBar.setTitle("Reaper");
        mToolBar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolBar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true); //设置返回键可用
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        //创建返回键，并实现打开关/闭监听
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
