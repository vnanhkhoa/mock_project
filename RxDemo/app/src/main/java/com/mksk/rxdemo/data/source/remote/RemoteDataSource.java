package com.mksk.rxdemo.data.source.remote;

import com.mksk.rxdemo.data.network.AppApi;
import com.mksk.rxdemo.data.network.response.Response;
import com.mksk.rxdemo.data.network.response.ResultResponse;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class RemoteDataSource implements AppApi {

    private static RemoteDataSource INSTANCE;
    private final AppApi mAppApi;

    public static RemoteDataSource getInstance(AppApi appApi) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(appApi);
        }
        return INSTANCE;
    }

    private RemoteDataSource(AppApi appApi) {
        mAppApi = appApi;
    }

    @Override
    public Flowable<ResultResponse> getSongVN() {
        return mAppApi.getSongVN();
    }

    @Override
    public Single<Response> loadPage(int page, int size) {
        return mAppApi.loadPage(page,size);
    }
}
