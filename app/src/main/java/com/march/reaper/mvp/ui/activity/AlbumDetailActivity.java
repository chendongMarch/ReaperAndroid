package com.march.reaper.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.march.bean.Album;
import com.march.bean.AlbumItemCollection;
import com.march.reaper.R;
import com.march.reaper.common.DbHelper;
import com.march.reaper.common.TitleBarView;
import com.march.reaper.mvp.ui.RootActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.mvp.presenter.impl.AlbumDetailPresenterImpl;
import com.march.reaper.mvp.ui.TitleActivity;
import com.march.reaper.widget.RecyclerGroupView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 专辑详情界面
 */
public class AlbumDetailActivity extends TitleActivity {

    @Bind(R.id.detail_albumlist_rv)
    RecyclerGroupView mAlbumsRgv;
    private Album mAlbumData;
    private AlbumDetailPresenterImpl mAlbumDetailPresenterImpl;
    private AlbumItemCollection mCol;
    private boolean isCollection;


    @Override
    protected int getLayoutId() {
        return R.layout.detail_activity;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mAlbumData = (Album) getIntent().getSerializableExtra(Constant.KEY_ALBUM_DETAIL_SHOW);
        mCol = new AlbumItemCollection(mAlbumData);
        isCollection = DbHelper.get().isAlbumCollection(mCol);
    }


    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        mTitleBar.setText("首页", "专辑详情", "大图");
        mAlbumsRgv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        View mHeadView = initHeader();
        mAlbumDetailPresenterImpl = new AlbumDetailPresenterImpl(mAlbumsRgv, self, mHeadView, mAlbumData);
        mAlbumDetailPresenterImpl.justQuery();
    }

    private View initHeader() {
        View mHeadView = getLayoutInflater().inflate(R.layout.detail_head_list, null);
        final ImageView mIsColIv = (ImageView) mHeadView.findViewById(R.id.iv_is_collection);
        mIsColIv.setImageResource(isCollection ? R.drawable.ic_collection : R.drawable.ic_not_collection);
        mIsColIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollection) {
                    DbHelper.get().removeAlbumCollection(mCol);
                    mIsColIv.setImageResource(R.drawable.ic_not_collection);
                } else {
                    DbHelper.get().addAlbumCollection(mCol);
                    mIsColIv.setImageResource(R.drawable.ic_collection);
                }
            }
        });
        final TagFlowLayout mKeyWdsFlow = (TagFlowLayout) mHeadView.findViewById(R.id.head_keywds_flow);
        TextView mDescTv = (TextView) mHeadView.findViewById(R.id.head_desc_tv);
        mDescTv.setText(mAlbumData.getAlbum_desc());
        String keyWdsStr = mAlbumData.getKey_words();
        if (keyWdsStr == null) {
            mKeyWdsFlow.setVisibility(View.GONE);
        } else {
            String[] strs = keyWdsStr.split(", ");
            mKeyWdsFlow.setAdapter(new TagAdapter<String>(strs) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) getLayoutInflater().inflate(R.layout.detail_item_keywds,
                            mKeyWdsFlow, false);
                    tv.setText(s);
                    return tv;
                }
            });
        }
        return mHeadView;
    }


    @Override
    protected void initEvents() {
        super.initEvents();
        mTitleBar.setListener(TitleBarView.POS_Right, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlbumDetailPresenterImpl.switchMode((TextView) v);
            }
        });
    }
}
