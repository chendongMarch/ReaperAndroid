package com.march.reaper.utils;

import com.march.bean.AlbumDetail;
import com.march.bean.RecommendAlbumItem;
import com.march.bean.WholeAlbumItem;
import com.march.reaper.mvp.model.AlbumDetailResponse;
import com.march.reaper.mvp.model.RecommendAlbumResponse;
import com.march.reaper.mvp.model.WholeAlbumResponse;

/**
 * Created by march on 16/7/2.
 * 简化三种类型的相似操作
 */
public abstract class Operate4Type {

    public void operate(Class cls) {
        if (cls == AlbumDetail.class || cls == AlbumDetailResponse.class) {
            isAlbumDetail();
        } else if (cls == RecommendAlbumItem.class || cls == RecommendAlbumResponse.class) {
            isRecommendAlbumItem();
        } else if (cls == WholeAlbumItem.class || cls == WholeAlbumResponse.class) {
            isWholeAlbumItem();
        }
    }

    protected abstract void isAlbumDetail();

    protected abstract void isRecommendAlbumItem();

    protected abstract void isWholeAlbumItem();
}
