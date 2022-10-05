package com.mksk.rxdemo.data.source;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.mksk.rxdemo.data.network.AppApi;
import com.mksk.rxdemo.data.network.NetWorkConfig;
import com.mksk.rxdemo.data.network.response.Datum;
import com.mksk.rxdemo.data.network.response.Response;
import com.mksk.rxdemo.data.network.response.ResultResponse;
import com.mksk.rxdemo.data.source.interf.Repository;
import com.mksk.rxdemo.data.source.paging.AirlinePagingSource;
import com.mksk.rxdemo.data.source.remote.RemoteDataSource;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class AppRepository extends Repository {

    private static AppRepository INSTANCE;
    private final AppApi mAppApi;
    private final AirlinePagingSource mAirlinePagingSource;

    public static AppRepository getINSTANCE() {

        if (INSTANCE == null) {
            NetWorkConfig netWorkConfig = NetWorkConfig.getInstance();
            INSTANCE = new AppRepository(netWorkConfig.getApi());
        }

        return INSTANCE;
    }

    private AppRepository(AppApi appApi) {
        mAppApi = RemoteDataSource.getInstance(appApi);
        mAirlinePagingSource = new AirlinePagingSource(mAppApi);
    }

    @Override
    public Flowable<ResultResponse> getSongVN() {
        return mAppApi.getSongVN();
    }

    @Override
    public Single<Response> loadPage(int page, int size) {
        return mAppApi.loadPage(page, size);
    }

    @Override
    public Flowable<PagingData<Datum>> pagingDatum() {
        Pager<Integer, Datum> pager = new Pager<>(
                new PagingConfig(20),
                () -> mAirlinePagingSource);
        return PagingRx.getFlowable(pager);
    }
}
