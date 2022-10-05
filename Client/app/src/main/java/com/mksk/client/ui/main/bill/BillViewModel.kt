package com.mksk.client.ui.main.bill

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mksk.client.data.models.RevenueWithRevenueDetail
import com.mksk.client.data.source.AppRepository

class BillViewModel(app:Application):AndroidViewModel(app) {
    private val repository = AppRepository.getInstant(app.applicationContext)
}