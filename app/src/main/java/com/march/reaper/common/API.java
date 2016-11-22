package com.march.reaper.common;

import com.march.lib.core.common.Logger;
import com.march.lib.core.common.Toaster;
import com.march.reaper.base.ReaperApplication;
import com.march.reaper.helper.NetHelper;
import com.march.reaper.helper.RetrofitHelper;
import com.march.reaper.imodel.AlbumDetailResponse;
import com.march.reaper.imodel.BaseResponse;
import com.march.reaper.imodel.BeautyAlbumResponse;
import com.march.reaper.imodel.UserInfo;
import com.march.reaper.imodel.VersionResponse;
import com.march.reaper.imodel.VideoFunResponse;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by march on 16/6/30.
 * API
 */
public class API {

    //public static final String BASE_TEST = "http://192.168.1.132:3000";
    public static final String BASE_TEST = "http://192.168.92.112:3000/";//y904-2
    //public static final String BASE_TEST = "http://192.168.2.145:3000";//lengwong
    //public static final String BASE_TEST = "http://192.168.31.132:3000";

    public static final String BASE_ONLINE = "http://reapernode.tiger.mopaasapp.com/";
    public static final String BASE = BASE_ONLINE;
    public static final String GET_RECOMMEND_ALBUM = "";
    public static final String GET_WHOLE_ALBUM = "";
    public static final String GET_ALBUM_DETAIL = "";
    //离线推荐专辑
    public static final String GET_OFFLINE_RECOMMEND = BASE + "offline/recommend";
    public static final String GET_OFFLINE_WHOLE = BASE + "offline/whole";
    public static final String GET_OFFLINE_DETAIL = BASE + "offline/detail";
    public static final String POST_ADD_USER = BASE + "addUser";
    public static final String POST_CHECK_USER = BASE + "checkUser";

    public static final String GET_SCAN_VIDEO = BASE + "scan/video";
    public static final String GET_SCAN_RECOMMEND = BASE + "scan/recommend";
    public static final String GET_SCAN_WHOLE = BASE + "scan/whole";
    public static final String GET_SCAN_ALBUM_DETAIL = BASE + "scan/detail";
    public static final String GET_LUCKY = BASE + "lucky";
    public static final String GET_CHECK_VERSION = BASE + "checkVersion";


    public static final String POST_AUTO_REGISTER = BASE + "autoRegister";
    public static final String POST_AUTO_RECORD = BASE + "autoRecord";


    private static ApiService apiService;

    public static void init() {
        Retrofit retrofit = RetrofitHelper.init();
        apiService = retrofit.create(ApiService.class);
    }

    public static ApiService api() {
        return apiService;
    }


    public interface ApiService {
        @GET(GET_SCAN_RECOMMEND)
        Flowable<BeautyAlbumResponse> getRecommendAlbum(@Query("limit") int limit, @Query("offset") long offset, @Query("albumtype") String albumtype);

        @GET(GET_SCAN_WHOLE)
        Flowable<BeautyAlbumResponse> getWholeAlbum(@Query("limit") int limit, @Query("offset") long offset);

        @GET(GET_SCAN_VIDEO)
        Flowable<VideoFunResponse> getFunVideo(@Query("limit") int limit, @Query("offset") long offset);

        @GET(GET_SCAN_ALBUM_DETAIL)
        Flowable<AlbumDetailResponse> getAlbumDetail(@Query("limit") int limit, @Query("offset") long offset, @Query("albumlink") String albumLink);

        @GET(GET_LUCKY)
        Flowable<BeautyAlbumResponse> getLuckAlbum(@Query("limit") int limit);

        @GET(GET_CHECK_VERSION)
        Flowable<VersionResponse> getLastVersion();

        @POST(POST_AUTO_REGISTER)
        Flowable<BaseResponse> postRegister(@Body UserInfo userInfo);

        @POST(POST_AUTO_RECORD)
        Flowable<BaseResponse> postRecord(@Body UserInfo userInfo);
    }


    public static <T extends BaseResponse> void enqueue(Flowable<T> flowable, final RequestCallback<T> callback) {
        if (!NetHelper.isConnected(ReaperApplication.get())) {
            Toaster.get().show(ReaperApplication.get(), "网络不给力～");
        }
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        Logger.e("获取到数据 " +
                                t.getData().size());
                        callback.onSucceed(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onError(throwable);
                        Logger.e("请求发生错误", throwable);
                    }
                });
    }
}
