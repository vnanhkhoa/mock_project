package com.mksk.rxdemo.data.source.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.mksk.rxdemo.data.network.AppApi;
import com.mksk.rxdemo.data.network.response.Datum;
import com.mksk.rxdemo.data.network.response.Response;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AirlinePagingSource extends RxPagingSource<Integer, Datum> {

    private final AppApi mAppApi;

    public AirlinePagingSource(AppApi mAppApi) {
        this.mAppApi = mAppApi;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Datum> state) {
        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, Datum> anchorPage = state.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Datum>> loadSingle(@NonNull LoadParams<Integer> loadParams) {

        int page = (loadParams.getKey() != null) ? loadParams.getKey() : 1;

        return mAppApi.loadPage(page, 20)
                .subscribeOn(Schedulers.io())
                .map(response -> toLoadResult(response, page)).onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, Datum> toLoadResult(@NonNull Response response, Integer page) {
        Integer prevKey = (page != null) ? page + 1 : null;
        Integer nextKey = (page == response.getTotalPages()) ? null : page - 1;

        return new LoadResult.Page<>(
                response.getData(),
                prevKey,
                nextKey,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }
}
