package com.mksk.client.ui.main.table

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mksk.client.data.source.AppRepository

class TableViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = AppRepository.getInstant(app.applicationContext)
}