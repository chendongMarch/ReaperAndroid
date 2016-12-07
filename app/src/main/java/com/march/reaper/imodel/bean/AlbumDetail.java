package com.march.reaper.imodel.bean;

import android.os.Parcel;

//import com.march.lib.adapter.common.ITypeAdapterModel;
import com.march.lib.adapter.common.ITypeAdapterModel;
import com.march.reaper.common.API;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.imodel.AlbumDetailResponse;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

import io.reactivex.Flowable;

@Entity
public class AlbumDetail implements ITypeAdapterModel, Detail {

    @Transient
    public static final int TYPE_SHU = 0;
    @Transient
    public static final int TYPE_HENG = 1;

    private String album_link;
    private String photo_src;
    private Integer width;
    private Integer height;
    private String time_stamp;
    private Boolean isFavorite;


    public AlbumDetail() {
    }

    @Keep
    public AlbumDetail(String album_link, String photo_src, Integer width, Integer height, String time_stamp, Boolean isFavorite) {
        this.album_link = album_link;
        this.photo_src = photo_src;
        this.width = width;
        this.height = height;
        this.time_stamp = time_stamp;
        this.isFavorite = isFavorite;
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

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public static void getAlbumDetail(int offset, int limit, String album_link, RequestCallback<AlbumDetailResponse> callback) {
        Flowable<AlbumDetailResponse> albumDetail = API.api().getAlbumDetail(limit, offset, album_link);
        API.enqueue(albumDetail, callback);
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
        dest.writeString(this.time_stamp);
        dest.writeValue(this.isFavorite);
    }

    protected AlbumDetail(Parcel in) {
        this.album_link = in.readString();
        this.photo_src = in.readString();
        this.width = (Integer) in.readValue(Integer.class.getClassLoader());
        this.height = (Integer) in.readValue(Integer.class.getClassLoader());
        this.time_stamp = in.readString();
        this.isFavorite = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    @Transient
    public static final Creator<AlbumDetail> CREATOR = new Creator<AlbumDetail>() {
        @Override
        public AlbumDetail createFromParcel(Parcel source) {
            return new AlbumDetail(source);
        }

        @Override
        public AlbumDetail[] newArray(int size) {
            return new AlbumDetail[size];
        }
    };
}
