package com.march.reaper.imodel.bean;

import android.os.Parcel;

import com.march.lib.adapter.common.ITypeAdapterModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DetailCollection implements ITypeAdapterModel, com.march.reaper.imodel.bean.Detail {


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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.album_link);
        dest.writeString(this.photo_src);
        dest.writeValue(this.width);
        dest.writeValue(this.height);
    }

    protected DetailCollection(Parcel in) {
        this.album_link = in.readString();
        this.photo_src = in.readString();
        this.width = (Integer) in.readValue(Integer.class.getClassLoader());
        this.height = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    @Transient
    public static final Creator<DetailCollection> CREATOR = new Creator<DetailCollection>() {
        @Override
        public DetailCollection createFromParcel(Parcel source) {
            return new DetailCollection(source);
        }

        @Override
        public DetailCollection[] newArray(int size) {
            return new DetailCollection[size];
        }
    };
}
