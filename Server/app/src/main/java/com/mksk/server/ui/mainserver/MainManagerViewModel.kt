package com.mksk.server.ui.mainserver

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mksk.server.data.models.Employee
import com.mksk.server.data.source.AppRepository
import kotlinx.coroutines.launch

class MainManagerViewModel(application: Application): AndroidViewModel(application) {

    private val repository = AppRepository.getInstant(application.applicationContext)

    fun insertEmployee(employee: Employee) {
        viewModelScope.launch {
            repository.insert(employee)
        }
    }
}