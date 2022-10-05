package com.mksk.client.ui.main.pay

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mksk.client.data.models.RevenueWithRevenueDetail
import com.mksk.client.data.source.AppRepository
import kotlinx.coroutines.launch

class PayViewModel(app:Application):AndroidViewModel(app) {
    private val repository = AppRepository.getInstant(app.applicationContext)

    fun deleteByIdRevenue(id: Int) {
        viewModelScope.launch {
            repository.deleteByIdRevenue(id)
        }
    }
}