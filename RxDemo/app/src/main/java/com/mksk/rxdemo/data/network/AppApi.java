package com.mksk.rxdemo.data.network;

import com.mksk.rxdemo.data.model.Airline;
import com.mksk.rxdemo.data.network.response.Response;
import com.mksk.rxdemo.data.network.response.ResultResponse;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppApi {

    @GET("/xhr/chart-realtime?songId=0&videoId=0&albumId=0&chart=song&time=-1")
    Flowable<ResultResponse> getSongVN();

    @GET("/v1/passenger")
    Single<Response> loadPage(@Query("page") int page, @Query("size") int size);
}
