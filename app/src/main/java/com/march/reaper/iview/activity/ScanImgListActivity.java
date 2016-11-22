package com.march.reaper.iview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.march.lib.core.common.Toaster;
import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.helper.ImageHelper;
import com.march.reaper.imodel.bean.Detail;
import com.march.reaper.widget.LeProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

/**
 * 图片查看,可以缩放,基于photoview
 */
public class ScanImgListActivity extends BaseReaperActivity {


    @Bind(R.id.vp_photo)
    ViewPager mImgListVp;
    private SparseArrayCompat<PhotoView> mCachePhotoView;
    private int mPos;
    private List<Detail> mDetailList;

    @Override
    protected int getLayoutId() {
        return R.layout.scan_img_list_activity;
    }


    public static <T extends Detail> void loadActivity(Activity activity, List<T> detail, int pos) {
        Intent intent = new Intent(activity, ScanImgListActivity.class);
        ArrayList<Detail> details = new ArrayList<>();
        details.addAll(detail);
        intent.putExtra(Constant.KEY_ALBUM_DETAIL_LIST_SCAN, details);
        intent.putExtra(Constant.KEY_POS, pos);
        activity.startActivity(intent);
    }


    @Override
    public void onInitDatas() {
        super.onInitDatas();
        mCachePhotoView = new SparseArrayCompat<>();
        mDetailList = (List<Detail>) getIntent().getSerializableExtra(Constant.KEY_ALBUM_DETAIL_LIST_SCAN);
        mPos = getIntent().getIntExtra(Constant.KEY_POS, 0);
    }


    @Override
    public void onInitViews(View view, Bundle save) {
        super.onInitViews(view, save);
        final ImgListPagerAdapter imgListPagerAdapter = new ImgListPagerAdapter();
        mImgListVp.setAdapter(imgListPagerAdapter);
        mImgListVp.setOffscreenPageLimit(3);
        mImgListVp.setCurrentItem(mPos);

        mImgListVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mCachePhotoView.get(position - 1) != null) {
                    mCachePhotoView.get(position - 1).setScale(1.0f, true);
                }
                if (mCachePhotoView.get(position + 1) != null) {
                    mCachePhotoView.get(position + 1).setScale(1.0f, true);

                }
                if (position == mDetailList.size() - 1) {
                    Toaster.get().show(mContext, "只有这么多了，返回上拉加载更多");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCachePhotoView.clear();
        mCachePhotoView = null;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isInitTitle() {
        return false;
    }

    class ImgListPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return mDetailList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View inflate = getLayoutInflater().inflate(R.layout.scan_img_list_item, mImgListVp, false);
            LeProgressView processView = (LeProgressView) inflate.findViewById(R.id.lpv_progress);
            processView.startLoadingWithPrepare();
            Detail detail = mDetailList.get(position);
            PhotoView iv = (PhotoView) inflate.findViewById(R.id.scan_iv);
            mCachePhotoView.put(position, iv);
            ImageHelper.loadImgProgress(mContext, detail.getPhoto_src(), iv, processView);
            container.addView(inflate);
            return inflate;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
