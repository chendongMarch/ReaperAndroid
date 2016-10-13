package com.march.reaper.iview.fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.base.fragment.BaseFragment;
import com.march.reaper.common.Constant;
import com.march.reaper.ipresenter.BaseNetFragmentPresenter;
import com.march.reaper.ipresenter.impl.AQ4WholePresenterImpl;
import com.march.reaper.ipresenter.impl.AlbumQueryPresenterImpl;
import com.march.reaper.widget.RecyclerGroupView;
import com.march.reaper.widget.TitleBarView;
import butterknife.Bind;
import butterknife.OnClick;
/**
 * Created by march on 16/7/1.
 * 专辑展示
 */
public class AlbumQueryFragment extends BaseFragment
        implements AlbumQueryPresenterImpl.AlbumQueryView {

    @Bind(R.id.albumquery_recycler)
    RecyclerGroupView mAlbumsRgv;
    private boolean isWholeAlbum;
    private String mRecommendType;
    private BaseNetFragmentPresenter mPresenter;

    public static AlbumQueryFragment newInst(String title, String type) {
        AlbumQueryFragment albumQueryFragment = new AlbumQueryFragment();
        Bundle args = new Bundle();
        args.putString(Constant.KEY_ALBUM_TITLE, title);
        args.putString(Constant.KEY_ALBUM_RECOMMEND_TYPE, type);
        albumQueryFragment.setArguments(args);
        return albumQueryFragment;
    }

    public static AlbumQueryFragment newInst() {
        AlbumQueryFragment albumQueryFragment = new AlbumQueryFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constant.KEY_IS_WHOLE_ALBUM, true);
        albumQueryFragment.setArguments(args);
        return albumQueryFragment;
    }


    @Override
    protected void initDatas() {
        super.initDatas();
        String mTitle = getArguments().getString(Constant.KEY_ALBUM_TITLE);
        mRecommendType = getArguments().getString(Constant.KEY_ALBUM_RECOMMEND_TYPE);
        if (mRecommendType == null)
            mRecommendType = "all";
        isWholeAlbum = getArguments().getBoolean(Constant.KEY_IS_WHOLE_ALBUM);
        mSelfName = AlbumQueryFragment.class.getSimpleName() + mTitle;
    }

    @Override
    protected boolean isInitTitle() {
        return true;
    }

    @Override
    protected void destroyPresenter() {
        mPresenter = null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.albumquery_fragment;
    }

    @Override
    protected void initViews(View view, Bundle save) {
        super.initViews(view, save);
        if (isWholeAlbum) {
            mTitleBarView.setText(null, "专辑", "大图");
            mAlbumsRgv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mPresenter = new AQ4WholePresenterImpl(this, (BaseReaperActivity) getActivity());
        } else {
            mTitleBarView.hide();
            mAlbumsRgv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mPresenter = new AlbumQueryPresenterImpl(this, (BaseReaperActivity) getActivity(), mRecommendType);
        }
        mPresenter.setRgv(mAlbumsRgv);
        mPresenter.justQuery();
    }

    @OnClick(R.id.tv_titlebar_right)
    public void clickBtn(View v) {
        mPresenter.switchMode();
    }

    @Override
    public void setModeTvText(String txt) {
        mTitleBarView.get(TitleBarView.POS_Right).setText(txt);
    }
}
