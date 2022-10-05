package com.mksk.server.ui.menu

import android.app.Application
import androidx.lifecycle.*
import com.mksk.server.data.models.Product
import com.mksk.server.data.source.AppRepository
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository.getInstant(application.applicationContext)

    val products = repository.getListProduct()

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            repository.insert(product)
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.update(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.delete(product)
        }
    }
}