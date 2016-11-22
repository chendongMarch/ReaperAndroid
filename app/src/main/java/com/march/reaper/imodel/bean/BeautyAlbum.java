package com.march.reaper.imodel.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.march.reaper.common.API;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.imodel.BeautyAlbumResponse;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

import io.reactivex.Flowable;
import org.greenrobot.greendao.annotation.Generated;

@Entity(createInDb = false)
public class BeautyAlbum implements Parcelable {

    private String album_type;
    private String key_words;
    private String album_link;
    private String album_cover;
    private String album_desc;
    private String time_stamp;
    private Boolean isFavorite;


    public BeautyAlbum() {
    }
    @Keep
    public BeautyAlbum(String album_type, String key_words, String album_link, String album_cover, String album_desc, String time_stamp, Boolean isFavorite) {
        this.album_type = album_type;
        this.key_words = key_words;
        this.album_link = album_link;
        this.album_cover = album_cover;
        this.album_desc = album_desc;
        this.time_stamp = time_stamp;

        this.isFavorite = isFavorite;
    }

    public String getAlbum_type() {
        return album_type;
    }

    public void setAlbum_type(String album_type) {
        this.album_type = album_type;
    }

    public String getKey_words() {
        return key_words;
    }

    public void setKey_words(String key_words) {
        this.key_words = key_words;
    }

    public String getAlbum_link() {
        return album_link;
    }

    public void setAlbum_link(String album_link) {
        this.album_link = album_link;
    }

    public String getAlbum_cover() {
        return album_cover;
    }

    public void setAlbum_cover(String album_cover) {
        this.album_cover = album_cover;
    }

    public String getAlbum_desc() {
        return album_desc;
    }

    public void setAlbum_desc(String album_desc) {
        this.album_desc = album_desc;
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


    @Override
    public String toString() {
        return "BeautyAlbum{" +
                "album_type='" + album_type + '\'' +
                ", key_words='" + key_words + '\'' +
                ", album_link='" + album_link + '\'' +
                ", album_cover='" + album_cover + '\'' +
                ", album_desc='" + album_desc + '\'' +
                ", time_stamp='" + time_stamp + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }

    public BeautyAlbum(com.march.reaper.imodel.bean.AlbumItemCollection collection) {
        this.album_link = collection.getAlbum_link();
        this.key_words = collection.getKey_words();
        this.album_cover = collection.getAlbum_cover();
        this.album_desc = collection.getAlbum_desc();
    }

    public static void getRecommend(int offset, int limit,
                                    String type, RequestCallback<BeautyAlbumResponse> callback) {
        Flowable<BeautyAlbumResponse> recommendAlbum = API.api().getRecommendAlbum(limit, offset, type);
        API.enqueue(recommendAlbum, callback);
    }

    public static void getWhole(int offset, int limit, final RequestCallback<BeautyAlbumResponse> callback) {
        Flowable<BeautyAlbumResponse> wholeAlbum = API.api().getWholeAlbum(limit, offset);
        API.enqueue(wholeAlbum, callback);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.album_type);
        dest.writeString(this.key_words);
        dest.writeString(this.album_link);
        dest.writeString(this.album_cover);
        dest.writeString(this.album_desc);
        dest.writeString(this.time_stamp);
        dest.writeValue(this.isFavorite);
    }

    protected BeautyAlbum(Parcel in) {
        this.album_type = in.readString();
        this.key_words = in.readString();
        this.album_link = in.readString();
        this.album_cover = in.readString();
        this.album_desc = in.readString();
        this.time_stamp = in.readString();
        this.isFavorite = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    @Transient
    public static final Creator<BeautyAlbum> CREATOR = new Creator<BeautyAlbum>() {
        @Override
        public BeautyAlbum createFromParcel(Parcel source) {
            return new BeautyAlbum(source);
        }

        @Override
        public BeautyAlbum[] newArray(int size) {
            return new BeautyAlbum[size];
        }
    };

}
