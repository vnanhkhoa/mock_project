package com.mksk.rxdemo.ui.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagingData;

import com.mksk.rxdemo.BasePresenter;
import com.mksk.rxdemo.data.network.response.Datum;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class PagingPresenter extends BasePresenter implements PagingContract.Presenter {

    private static PagingPresenter INSTANCE;
    private final PagingContract.View mView;
    private final MutableLiveData<PagingData<Datum>> _data = new MutableLiveData<>();
    public final LiveData<PagingData<Datum>> data = _data;

    public static PagingPresenter getInstance(PagingContract.View view) {

        if (INSTANCE == null) {
            INSTANCE = new PagingPresenter(view);
        }

        return INSTANCE;
    }


    private PagingPresenter(PagingContract.View view) {
        mView = view;
    }

    public void pagingDatum() {
        addDisposable(repository
                .pagingDatum()
                .subscribeOn(Schedulers.io())
                .subscribe(_data::postValue));
    }

}
