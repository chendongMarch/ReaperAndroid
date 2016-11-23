package com.march.reaper.imodel.bean;


import android.os.Parcelable;

public interface Detail extends Parcelable{

    String getAlbum_link();

    String getPhoto_src();

    Integer getWidth();

    Integer getHeight();
}
