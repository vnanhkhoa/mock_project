package com.mksk.server.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _isShowActionBar = MutableLiveData(false)
    val isShowActionBar: LiveData<Boolean>
        get() = _isShowActionBar


    fun showActionBar(isShow: Boolean) {
        _isShowActionBar.value = isShow
    }
}