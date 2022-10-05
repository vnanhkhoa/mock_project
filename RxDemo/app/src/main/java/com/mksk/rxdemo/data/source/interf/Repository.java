package com.mksk.rxdemo.data.source.interf;

import androidx.paging.PagingData;

import com.mksk.rxdemo.data.network.AppApi;
import com.mksk.rxdemo.data.network.response.Datum;

import io.reactivex.rxjava3.core.Flowable;

public abstract class Repository implements AppApi {
    public abstract Flowable<PagingData<Datum>> pagingDatum();
}
