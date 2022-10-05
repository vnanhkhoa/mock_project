package com.mksk.server.ui.table

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mksk.server.data.models.Table
import com.mksk.server.data.source.AppRepository
import kotlinx.coroutines.launch

class TableViewModel(application: Application):AndroidViewModel(application) {
    private val repository = AppRepository.getInstant(application.applicationContext)

    val table = repository.getListTable()
    val lastRecordTable = repository.getLastRecord()

    fun insertTable(t: Table){
        viewModelScope.launch {
            repository.insert(t)
        }
    }

    fun deleteTable(t:Table){
        viewModelScope.launch {
            repository.delete(t)
        }
    }

    fun updateTable(t:Table){
        viewModelScope.launch {
            repository.update(t)
        }
    }
}