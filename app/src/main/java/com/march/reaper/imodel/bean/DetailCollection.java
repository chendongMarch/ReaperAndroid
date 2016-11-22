package com.march.reaper.imodel.bean;

import com.march.lib.adapter.common.IAdapterModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class DetailCollection implements IAdapterModel, java.io.Serializable, com.march.reaper.imodel.bean.Detail {


    @Transient
    public static final int TYPE_SHU = 0;
    @Transient
    public static final int TYPE_HENG = 1;
    

    private String album_link;
    @Id
    private String photo_src;
    private Integer width;
    private Integer height;


    public DetailCollection() {
    }

    public DetailCollection(String photo_src) {
        this.photo_src = photo_src;
    }

    @Keep
    public DetailCollection(String album_link, String photo_src, Integer width, Integer height) {
        this.album_link = album_link;
        this.photo_src = photo_src;
        this.width = width;
        this.height = height;
    }

    public String getAlbum_link() {
        return album_link;
    }

    public void setAlbum_link(String album_link) {
        this.album_link = album_link;
    }

    public String getPhoto_src() {
        return photo_src;
    }

    public void setPhoto_src(String photo_src) {
        this.photo_src = photo_src;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public DetailCollection(com.march.reaper.imodel.bean.Detail item) {
        this.album_link = item.getAlbum_link();
        this.photo_src = item.getPhoto_src();
        this.width = item.getWidth();
        this.height = item.getHeight();
    }


    @Override
    public int getRvType() {
        if (height > width)
            return 0;
        else
            return 1;
    }

}
