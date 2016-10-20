package com.march.reaper.iview.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.march.quickrvlibs.adapter.RvViewHolder;
import com.march.quickrvlibs.adapter.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.reaper.R;
import com.march.reaper.base.fragment.BaseFragment;
import com.march.reaper.common.API;
import com.march.reaper.common.Constant;
import com.march.reaper.helper.ShareHelper;
import com.march.reaper.helper.Toaster;
import com.march.reaper.imodel.VersionResponse;
import com.march.reaper.iview.activity.AboutActivity;
import com.march.reaper.iview.activity.AlbumDetailActivity;
import com.march.reaper.utils.AppUtils;
import com.march.reaper.utils.QueryUtils;

import butterknife.Bind;

/**
 * 我的页面
 */
public class HomeMineFragment extends BaseFragment {

    @Bind(R.id.rv_mine)
    RecyclerView mContentRv;

    @Override
    protected boolean isInitTitle() {
        return true;
    }

    public static HomeMineFragment newInst(){
        return new HomeMineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_fragment;
    }

    @Override
    public void onInitDatas() {
        super.onInitDatas();
        mSelfName = HomeMineFragment.class.getSimpleName();
    }

    @Override
    public void onInitViews(View view, Bundle save) {
        super.onInitViews(view, save);
        mTitleBarView.setText(null, "我的", null);
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        SimpleRvAdapter<String> mContentAdapter = new SimpleRvAdapter<String>(getActivity(), Constant.MINE_CONTENT_LIST, R.layout.mine_item_content) {
            @Override
            public void bindData4View(RvViewHolder holder, String data, int pos) {
                if (pos == 2) {
                    holder.getParentView().getLayoutParams().height = 0;
                    return;
                }
                holder.setText(R.id.tv_mine_item_info, data);
            }
        };
        mContentAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder, String data) {
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
//                AlbumCollectionActivity.loadActivity(getActivity());
                break;
            case 2:
                //数据离线
                Toaster.get().show(mContext, "敬请期待");
                break;
            case 3:
                //分享
                ShareHelper.get(mContext).shareText("Reaper精品美图", "Reaper精选美图,火热上线,访问官网 " + getString(R.string.official_website));
                break;
            case 4:
                //检查更新
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
                    Toaster.get().show(mContext, "当前是最新版本.");
                else {
                    new AlertDialog.Builder(mContext)
                            .setTitle("版本更新")
                            .setMessage("当前版本:" + currentCode + "\n最新版本:" + versionCode)
                            .setPositiveButton("去更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri uri = Uri.parse(getString(R.string.official_website));
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                }
            }

            @Override
            public void error(Exception e) {

            }
        });
    }

}
