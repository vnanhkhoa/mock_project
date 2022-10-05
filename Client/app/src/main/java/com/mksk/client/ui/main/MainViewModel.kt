package com.mksk.client.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mksk.client.data.source.AppRepository
import com.mksk.server.ServerAIDL
import com.mksk.server.data.models.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository.getInstant(application.applicationContext)

    private val tableIsOrdering = repository.getTableIsOrdering()
    private val flowTable = MutableStateFlow(listOf<Table>())
    private val _tables = flowTable.combine(tableIsOrdering) { tables,idTables ->
        tables.map {
            it.copy(status = idTables.contains(it.id))
        }
    }

    val tables = _tables.asLiveData()

    private val _employee = MutableLiveData<Employee>()
    val employee: LiveData<Employee> = _employee

    private val _mService = MutableLiveData<ServerAIDL>()
    val service: LiveData<ServerAIDL> = _mService

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _listEmployee = MutableLiveData<List<Employee>>()
    val listEmployees: LiveData<List<Employee>> = _listEmployee

    private val _isShowActionBar = MutableLiveData(false)
    val isShowActionBar: LiveData<Boolean>
        get() = _isShowActionBar


    fun showActionBar(isShow: Boolean) {
        _isShowActionBar.value = isShow
    }

    fun setService(serverAIDL: ServerAIDL) {
        _mService.postValue(serverAIDL)
    }

    fun setEmployee(temp: Employee) {
        _employee.value = temp
    }

    fun setListEmployee(temp: List<Employee>) {
        _listEmployee.value = temp
    }

    fun setListProduct(temp: List<Product>) {
        _products.value = temp
    }

    fun setListTable(temp: List<Table>) {
        viewModelScope.launch {
            flowTable.emit(temp)
        }
    }

    fun deleteRevenue(revenue: Revenue) {
        viewModelScope.launch {
            repository.delete(revenue)
        }
    }

}