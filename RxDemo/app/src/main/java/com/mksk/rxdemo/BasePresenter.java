package com.mksk.rxdemo;

import com.mksk.rxdemo.data.source.AppRepository;
import com.mksk.rxdemo.data.source.interf.Repository;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class BasePresenter {

    protected Repository repository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public BasePresenter() {
        this.repository = AppRepository.getINSTANCE();
    }

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
}
