package com.march.reaper.mvp.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.march.bean.WholeAlbumItem;
import com.march.bean.WholeAlbumItem;
import com.march.quickrvlibs.RvViewHolder;
import com.march.quickrvlibs.SimpleRvAdapter;
import com.march.quickrvlibs.inter.OnItemClickListener;
import com.march.quickrvlibs.module.LoadMoreModule;
import com.march.reaper.R;
import com.march.reaper.common.Constant;
import com.march.reaper.common.DbHelper;
import com.march.reaper.mvp.presenter.FragmentPresenter;
import com.march.reaper.mvp.ui.activity.AlbumDetailActivity;
import com.march.reaper.utils.DisplayUtils;
import com.march.reaper.utils.Lg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by march on 16/7/2.
 * 专辑页面
 */
public class AlbumQuery4WholePresenterImpl extends FragmentPresenter {

    private static final int mPreLoadNum = 2;
    private Context context;
    private RecyclerView mAlbumsRv;
    private List<WholeAlbumItem> datas;
    private SimpleRvAdapter<WholeAlbumItem> mAlbumAdapter;
    private int mWidth;
    private int offset = 0, limit = 40;
    private boolean isBig = false;


    public AlbumQuery4WholePresenterImpl(Context context, RecyclerView mAlbumsRv) {
        this.mAlbumsRv = mAlbumsRv;
        this.context = context;
        datas = new ArrayList<>();
        mWidth = DisplayUtils.getScreenWidth();
    }

    //从数据库查询数据
    public void queryDatas() {
        if (offset == -1)
            return;
        DbHelper.get().queryWholeAlbum(offset, limit, new DbHelper.OnQueryReadyListener<WholeAlbumItem>() {
            @Override
            public void queryReady(List<WholeAlbumItem> list) {
                if (list.size() <= 0) {
                    offset = -1;
                    Lg.e("没有数据了");
                    return;
                }
                datas.addAll(list);
                if (mAlbumAdapter == null) {
                    createRvAdapter();
                    mAlbumsRv.setAdapter(mAlbumAdapter);
                } else {
                    mAlbumAdapter.notifyDataSetChanged();
                }
                //查询成功,offset增加
                offset += limit;
                mAlbumAdapter.finishLoad();
            }
        });
    }

    //构建adapter
    private void createRvAdapter() {
        mAlbumAdapter = new SimpleRvAdapter<WholeAlbumItem>(context, datas, R.layout.albumquery_item_album) {
            @Override
            public void bindData4View(RvViewHolder holder, WholeAlbumItem data, int pos) {
                int height = (int) (mWidth * (2f / 3f));
                holder.setImg(context, R.id.albumquery_item_iv, data.getAlbum_cover(), mWidth, height, R.mipmap.demo);
                if (isBig)
                    holder.setVisibility(R.id.albumquery_item_tv, View.VISIBLE).setText(R.id.albumquery_item_tv, data.getAlbum_desc());
                else
                    holder.setVisibility(R.id.albumquery_item_tv, View.GONE);
            }
        };
        mAlbumAdapter.setOnItemClickListener(new OnItemClickListener<RvViewHolder>() {
            @Override
            public void onItemClick(int pos, RvViewHolder holder) {
                Intent intent = new Intent(context, AlbumDetailActivity.class);
                intent.putExtra(Constant.KEY_ALBUM_DETAIL_SHOW, datas.get(pos));
                context.startActivity(intent);
            }
        });
        mAlbumAdapter.addLoadMoreModule(mPreLoadNum, new LoadMoreModule.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Lg.e("加载更多  " + offset);
                queryDatas();
            }
        });
    }


    public void switchMode(TextView tv) {
        isBig = !isBig;
        if (isBig) {
            tv.setText("小图");
            mAlbumsRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        } else {
            tv.setText("大图");
            mAlbumsRv.setLayoutManager(new GridLayoutManager(context, 2));
        }
    }

}
