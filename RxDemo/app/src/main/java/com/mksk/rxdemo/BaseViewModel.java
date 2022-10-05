package com.mksk.rxdemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mksk.rxdemo.data.source.AppRepository;
import com.mksk.rxdemo.data.source.interf.Repository;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class BaseViewModel extends AndroidViewModel {

    protected Repository mRepository;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getINSTANCE();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
}
