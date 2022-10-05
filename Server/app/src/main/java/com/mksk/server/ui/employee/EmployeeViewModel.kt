package com.mksk.server.ui.employee

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mksk.server.data.models.Employee
import com.mksk.server.data.source.AppRepository
import kotlinx.coroutines.launch

class EmployeeViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = AppRepository.getInstant(app.applicationContext)

    val getInfo = repository.getInfo()

    fun addEmployee(e:Employee){
        viewModelScope.launch {
            repository.insert(e)
        }
    }

    fun updateEmployee(e:Employee){
        viewModelScope.launch {
            repository.update(e)
        }
    }

    fun deleteEmployee(e: Employee){
        viewModelScope.launch {
            repository.delete(e)
        }
    }

}