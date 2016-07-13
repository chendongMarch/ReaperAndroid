package com.march.reaper.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.reaper.R;
import com.march.reaper.common.Constant;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.mvp.ui.RootFragment;
import com.march.reaper.mvp.ui.TitleFragment;
import com.march.reaper.mvp.ui.activity.AboutActivity;
import com.march.reaper.mvp.ui.activity.AlbumCollectionActivity;
import com.march.reaper.mvp.ui.activity.AlbumDetailActivity;
import com.march.reaper.mvp.ui.activity.OffLineDataActivity;
import com.march.reaper.utils.SysShareUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的页面
 */
public class MineFragment extends TitleFragment {

    @Bind(R.id.rv_mine)
    RecyclerView mContentRv;

    @Override
    protected int getLayoutId() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mSelfName = MineFragment.class.getSimpleName();
    }

    @Override
    protected void initViews(View view, Bundle save) {
        super.initViews(view, save);
        mTitleBar.setText(null, "我的", null);
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        SimpleRvAdapter<String> mContentAdapter = new SimpleRvAdapter<String>(getActivity(), Constant.MINE_CONTENT_LIST, R.layout.mine_item_content) {
            @Override
            public void bindData4View(RvViewHolder holder, String data, int pos) {
                holder.setText(R.id.tv_mine_item_info, data);
            }
        };
        mContentAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                handleMineOperate(pos);
            }
        });
        mContentRv.setAdapter(mContentAdapter);
    }

    private void handleMineOperate(int pos) {
        switch (pos) {
            case 0:
                //照片收藏
                AlbumDetailActivity.loadActivity4Collection(getActivity());
                break;
            case 1:
                //专辑收藏
                AlbumCollectionActivity.loadActivity(getActivity());
                break;
            case 2:
                //数据离线
                break;
            case 3:
                //分享
                SysShareUtils.newInst(getActivity()).shareText("美图软件", "精选美女图片尽在指尖");
                break;
            case 4:
                //设置
                break;
            case 5:
//                关于
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;


        }
    }

    @OnClick(R.id.mine_offline)
    public void clickBtn(View v) {
        startActivity(new Intent(getActivity(), OffLineDataActivity.class));
    }
}
