package com.march.reaper.mvp.ui.activity;

import android.support.v4.view.LinkagePager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.march.reaper.R;
import com.march.reaper.ReaperActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.LinkageCoverTransformer;
import me.crosswall.lib.coverflow.core.LinkagePagerContainer;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class CoverFlowActivity extends ReaperActivity {



    private List<String> strs;

    private List<ImageView> ivs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cover_flow;
    }

    @Override
    protected void initViews(Bundle save) {
        super.initViews(save);
        strs = new ArrayList<>();
        ivs = new ArrayList<>();
        for (int i = 0; i <1; i++) {
            strs.add(" " + i);
            ImageView iv = new ImageView(self);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setImageResource(R.mipmap.ic_launcher);
            ivs.add(iv);
        }

        LinkagePagerContainer mContainer = (LinkagePagerContainer) findViewById(R.id.pager_container);
        final LinkagePager pager = mContainer.getViewPager();
        PagerAdapter adapter = new MyAdapter();
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());
        pager.setClipChildren(false);
        pager.setPageTransformer(false,new LinkageCoverTransformer(0.4f,-60f,0f,0f));


    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return ivs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = ivs.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(ivs.get(position));
        }
    }
}
