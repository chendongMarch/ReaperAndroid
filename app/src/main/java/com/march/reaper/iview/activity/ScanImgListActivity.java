package com.march.reaper.iview.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.march.lib.core.common.DimensionHelper;
import com.march.lib.core.common.Toaster;
import com.march.lib.core.mvp.presenter.BasePresenter;
import com.march.lib.view.LeProgressView;
import com.march.reaper.R;
import com.march.reaper.base.activity.BaseReaperActivity;
import com.march.reaper.common.Constant;
import com.march.reaper.helper.ActivityHelper;
import com.march.reaper.helper.ImageHelper;
import com.march.reaper.imodel.bean.Detail;
import com.march.reaper.iview.dialog.MenuDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片查看,可以缩放,基于photoview
 */
public class ScanImgListActivity extends BaseReaperActivity {


    @Bind(R.id.vp_photo)
    ViewPager mImgListVp;
    @Bind(R.id.center_tv)
    TextView mCenterTv;
    @Bind(R.id.title_bar)
    View mTitleBar;

    private int mPos;
    private boolean isShown = true;

    private SparseArrayCompat<PhotoView> mCachePhotoView;
    private List<Detail> mDetailList;
    private MenuDialog mMoreMenuDialog;
    private MenuDialog mWallPaperMenuDialog;

    private Runnable mEndRunnable;

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
        ActivityHelper.translateStart(activity);
    }


    @Override
    public void onInitDatas() {
        super.onInitDatas();
        mCachePhotoView = new SparseArrayCompat<>();
        mDetailList = getIntent().getParcelableArrayListExtra(Constant.KEY_ALBUM_DETAIL_LIST_SCAN);
        mPos = getIntent().getIntExtra(Constant.KEY_POS, 0);
    }


    @Override
    public void onInitViews(View view, Bundle save) {
        super.onInitViews(view, save);
        updateCenterText();
        mEndRunnable = new Runnable() {
            @Override
            public void run() {
                showLoading(false);
            }
        };

        mImgListVp.setAdapter(new ImgListPagerAdapter());
        mImgListVp.setOffscreenPageLimit(3);
        mImgListVp.setCurrentItem(mPos);
    }

    @Override
    public void onInitEvents() {
        super.onInitEvents();
        mImgListVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPos = position;
                if (mCachePhotoView.get(position - 1) != null) {
                    mCachePhotoView.get(position - 1).setScale(1.0f, true);
                }
                if (mCachePhotoView.get(position + 1) != null) {
                    mCachePhotoView.get(position + 1).setScale(1.0f, true);
                }
                updateCenterText();
                if (position == mDetailList.size() - 1) {
                    Toaster.get().show(mContext, "只有这么多了，返回上拉加载更多");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                hideTitleBar();
            }
        });
    }

    /**
     * 显示菜单
     */
    private void showMoreMenuDialog() {
        if (checkDialog2Show(mMoreMenuDialog))
            return;
        List<MenuDialog.MyMenu> myMenus = new ArrayList<>();
        myMenus.add(new MenuDialog.MyMenu(0, "下载到本地"));
        myMenus.add(new MenuDialog.MyMenu(1, "设置为壁纸"));
        myMenus.add(new MenuDialog.MyMenu(2, "分享给好友"));

        mMoreMenuDialog = new MenuDialog(mContext, "更多", myMenus, new MenuDialog.OnMyMenuItemClickListener() {
            @Override
            public void onClick(MenuDialog dialog, int pos, View view, MenuDialog.MyMenu menu) {
                String photo_src = mDetailList.get(mPos).getPhoto_src();
                switch (menu.id) {
                    case 0:
                        showLoading(true);
                        ImageHelper.saveToLocal(mContext, photo_src, false, mEndRunnable);
                        break;
                    case 1:
                        showWallpaperSetDialog(photo_src);
                        break;
                    case 2:
                        showLoading(true);
                        ImageHelper.saveToLocal(mContext, photo_src, true, mEndRunnable);
                        break;
                }
            }
        });
        mMoreMenuDialog.show();
    }

    private void updateCenterText() {
        mCenterTv.setText("专辑详情(" + mPos + "/" + mDetailList.size() + ")");
    }

    @OnClick({R.id.back_tv, R.id.right_tv})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.back_tv:
                if (mWallPaperMenuDialog!=null&&mWallPaperMenuDialog.isShowing()) {
                    mWallPaperMenuDialog.dismiss();
                } else if (mMoreMenuDialog!=null && mMoreMenuDialog.isShowing()) {
                    mMoreMenuDialog.dismiss();
                } else {
                   onBackPressed();
                }
                break;
            case R.id.right_tv:
                showMoreMenuDialog();
                break;
        }
    }

    /**
     * 显示设置壁纸的dialog
     *
     * @param photo_src 资源
     */
    private void showWallpaperSetDialog(final String photo_src) {
        if (checkDialog2Show(mWallPaperMenuDialog))
            return;
        List<MenuDialog.MyMenu> menus = new ArrayList<>();
        menus.add(new MenuDialog.MyMenu(0, "锁屏壁纸"));
        menus.add(new MenuDialog.MyMenu(1, "桌面壁纸"));
        menus.add(new MenuDialog.MyMenu(2, "同时设置"));
        mWallPaperMenuDialog = new MenuDialog(mContext, "设置壁纸", menus, new MenuDialog.OnMyMenuItemClickListener() {
            @Override
            public void onClick(MenuDialog dialog, int pos, View view, MenuDialog.MyMenu menu) {
                showLoading(true);
                switch (menu.id) {
                    case 0:
                        ImageHelper.setWallPaper(mContext, false, true, photo_src, mEndRunnable);
                        break;
                    case 1:
                        ImageHelper.setWallPaper(mContext, true, false, photo_src, mEndRunnable);
                        break;
                    case 2:
                        ImageHelper.setWallPaper(mContext, true, true, photo_src, mEndRunnable);
                        break;
                }
            }
        });
        mWallPaperMenuDialog.show();
    }

    private void showTitleBar() {
        if (isShown)
            return;
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("alpha", 0f,
                1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("translationY", -DimensionHelper.dp2px(mContext, 50),
                0);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mTitleBar, pvhY, pvhZ).setDuration(200);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        isShown = true;
    }

    private void hideTitleBar() {
        if (!isShown)
            return;
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("translationY", 0f,
                -DimensionHelper.dp2px(mContext, 50));
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mTitleBar, pvhY, pvhZ).setDuration(200);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        isShown = false;
    }

    private void toggleTitleBar() {
        if (isShown) {
            hideTitleBar();
        } else {
            showTitleBar();
        }
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
            final LeProgressView processView = (LeProgressView) inflate.findViewById(R.id.lpv_progress);
            final Detail detail = mDetailList.get(position);
            final PhotoView iv = (PhotoView) inflate.findViewById(R.id.scan_iv);
            iv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    toggleTitleBar();
                }

                @Override
                public void onOutsidePhotoTap() {
                    toggleTitleBar();
                }
            });
            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showMoreMenuDialog();
                    return false;
                }
            });
            processView.startLoadingWithPrepare(new Runnable() {
                @Override
                public void run() {
                    ImageHelper.loadImgProgress(mActivity, detail.getPhoto_src(), iv, processView);
                }
            });
            mCachePhotoView.put(position, iv);
            container.addView(inflate);
            return inflate;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
