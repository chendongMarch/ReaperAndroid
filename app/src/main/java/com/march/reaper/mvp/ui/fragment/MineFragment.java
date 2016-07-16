package com.march.reaper.mvp.ui.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.reaper.R;
import com.march.reaper.common.API;
import com.march.reaper.common.Constant;
import com.march.reaper.listener.OnDialogBtnListener;
import com.march.reaper.mvp.model.VersionResponse;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.mvp.ui.RootDialog;
import com.march.reaper.mvp.ui.RootFragment;
import com.march.reaper.mvp.ui.TitleFragment;
import com.march.reaper.mvp.ui.activity.AboutActivity;
import com.march.reaper.mvp.ui.activity.AlbumCollectionActivity;
import com.march.reaper.mvp.ui.activity.AlbumDetailActivity;
import com.march.reaper.mvp.ui.activity.OffLineDataActivity;
import com.march.reaper.mvp.ui.dialog.CommonMsgDialog;
import com.march.reaper.utils.AppUtils;
import com.march.reaper.utils.QueryUtils;
import com.march.reaper.utils.SysShareUtils;
import com.march.reaper.utils.To;

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
                if (pos == 2 || pos == 4) {
                    holder.getParentView().getLayoutParams().height = 0;
                    return;
                }
//                if (pos == 5) {
//                    holder.setVisibility(R.id.mine_item_blackline, View.GONE);
//                }
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
                To.show("敬请期待");
                break;
            case 3:
                //分享
                SysShareUtils.newInst(getActivity()).shareText("Reaper精品美图", "Reaper精选美图,火热上线,访问官网 " + getString(R.string.official_website));
                break;
            case 4:
                //检查更新
                To.show("敬请期待");
                checkUpdateVersion();
                break;
            case 5:
                //关于
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
        }
    }

    //监测新版本,打开浏览器下载
    private void checkUpdateVersion() {
        QueryUtils.get().get(API.GET_CHECK_VERSION, VersionResponse.class, new QueryUtils.OnQueryOverListener<VersionResponse>() {
            @Override
            public void queryOver(VersionResponse rst) {
                int versionCode = rst.getVersionCode();
                int currentCode = AppUtils.getVersionCode();
                if (currentCode >= versionCode)
                    To.show("当前是最新版本.");
                else
                    new CommonMsgDialog(getActivity()).setBtn(CommonMsgDialog.Btn_OK, "去更新", new OnDialogBtnListener() {
                        @Override
                        public void onBtnClick(RootDialog dialog, TextView btn) {
                            Uri uri = Uri.parse(getString(R.string.official_website));
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    }).setBtn(CommonMsgDialog.Btn_CANCEL)
                            .show("版本更新", "当前版本:" + currentCode + "\n最新版本:" + versionCode);
            }

            @Override
            public void error(Exception e) {

            }
        });
    }

}
