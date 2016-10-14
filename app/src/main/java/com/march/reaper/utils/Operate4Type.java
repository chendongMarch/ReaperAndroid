package com.march.reaper.utils;

import com.march.bean.AlbumDetail;
import com.march.bean.BeautyAlbum;
import com.march.reaper.imodel.AlbumDetailResponse;
import com.march.reaper.imodel.BeautyAlbumResponse;

/**
 * Created by march on 16/7/2.
 * 简化三种类型的相似操作
 */
public abstract class Operate4Type {

    public void operate(Class cls) {
        if (cls == AlbumDetail.class || cls == AlbumDetailResponse.class) {
            isAlbumDetail();
        } else if (cls == BeautyAlbum.class || cls == BeautyAlbumResponse.class) {
            isBeautyAlbumItem();
        }
    }

    protected abstract void isAlbumDetail();

    protected abstract void isBeautyAlbumItem();

}
