package com.march.reaper.mvp.model;

import com.march.bean.RecommendAlbumItem;

import java.util.List;

/**
 * Created by march on 16/7/1.
 *
 */
public class RecommendAlbumResponse implements BaseResponse{

    List<RecommendAlbumItem> data;

    public RecommendAlbumResponse(List<RecommendAlbumItem> data) {
        this.data = data;
    }

    public List<RecommendAlbumItem> getData() {
        return data;
    }

    public void setData(List<RecommendAlbumItem> data) {
        this.data = data;
    }
}
