package com.march.reaper.imodel.bean;


import com.march.reaper.common.API;
import com.march.reaper.common.RequestCallback;
import com.march.reaper.imodel.VideoFunResponse;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

import io.reactivex.Flowable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class VideoFun {

    private String time_stamp;
    private String describe;
    private String linkPageUrl;
    private Integer width;
    private Integer height;
    private Integer publishTime;
    private String videoCategory;
    private Integer videoCategoryType;
    private String videoType;
    private String videoPlayUrl;


    public VideoFun() {
    }

    @Keep
    public VideoFun(String time_stamp, String describe, String linkPageUrl, Integer width, Integer height, Integer publishTime, String videoCategory, Integer videoCategoryType, String videoType, String videoPlayUrl) {
        this.time_stamp = time_stamp;
        this.describe = describe;
        this.linkPageUrl = linkPageUrl;
        this.width = width;
        this.height = height;
        this.publishTime = publishTime;
        this.videoCategory = videoCategory;
        this.videoCategoryType = videoCategoryType;
        this.videoType = videoType;
        this.videoPlayUrl = videoPlayUrl;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLinkPageUrl() {
        return linkPageUrl;
    }

    public void setLinkPageUrl(String linkPageUrl) {
        this.linkPageUrl = linkPageUrl;
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

    public Integer getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Integer publishTime) {
        this.publishTime = publishTime;
    }

    public String getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(String videoCategory) {
        this.videoCategory = videoCategory;
    }

    public Integer getVideoCategoryType() {
        return videoCategoryType;
    }

    public void setVideoCategoryType(Integer videoCategoryType) {
        this.videoCategoryType = videoCategoryType;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getVideoPlayUrl() {
        return videoPlayUrl;
    }

    public void setVideoPlayUrl(String videoPlayUrl) {
        this.videoPlayUrl = videoPlayUrl;
    }


    public static void getVideoFunData(int offset, int limit,
                                       final RequestCallback<VideoFunResponse> callback) {

        Flowable<VideoFunResponse> funVideo = API.api().getFunVideo(limit, offset);
        API.enqueue(funVideo, callback);
    }

}
