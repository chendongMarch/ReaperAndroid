package com.march.reaper.mvp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.march.bean.Album;
import com.march.reaper.R;
import com.march.reaper.RootActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.mvp.presenter.impl.AlbumDetailPresenterImpl;
import com.march.reaper.utils.To;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 专辑详情界面
 */
public class AlbumDetailActivity extends RootActivity {

    @Bind(R.id.detail_albumlist_rv)
    RecyclerView mAlbumsRv;
    private Album mAlbumData;
    private AlbumDetailPresenterImpl mAlbumDetailPresenterImpl;

    @Override
    protected int getLayoutId() {
        return R.layout.detail_activity;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mAlbumData = (Album) getIntent().getSerializableExtra(Constant.KEY_ALBUM_DETAIL_SHOW);
    }


    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        mAlbumsRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        View mHeadView = getLayoutInflater().inflate(R.layout.detail_head_list, null);
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
        mAlbumDetailPresenterImpl = new AlbumDetailPresenterImpl(self, mAlbumsRv, mHeadView, mAlbumData);
        mAlbumDetailPresenterImpl.queryNetDatas();
    }

    @OnClick({R.id.detail_switchmode, R.id.detail_back})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.detail_back:
                finish();
                break;
            case R.id.detail_switchmode:
                mAlbumDetailPresenterImpl.switchMode((TextView) view);
//                To.show("长按图片查看大图");
                break;
        }

    }
}
