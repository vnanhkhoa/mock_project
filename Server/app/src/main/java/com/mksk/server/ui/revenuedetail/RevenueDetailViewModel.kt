package com.mksk.server.ui.revenuedetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mksk.server.data.models.RevenueForProduct
import com.mksk.server.data.source.AppRepository

class RevenueDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository.getInstant(application.applicationContext)
    private lateinit var revenueForProducts: LiveData<List<RevenueForProduct>>


    fun getRevenueForProducts(date : String):LiveData<List<RevenueForProduct>> {
        revenueForProducts = repository.getListProductForDate(date)
        return revenueForProducts
    }
}