package com.mksk.client.ui.main.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mksk.client.data.source.AppRepository
import com.mksk.server.data.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MenuOrderViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppRepository.getInstant(application.applicationContext)

    private val products = MutableStateFlow(listOf<Product>())
    val productOrder = liveData {
        products.collect {
            val result = it.associate { product ->
                product.productID!! to product.toProductOrder()
            }
            emit(result)
        }
    }

    fun getListProduct(temp: List<Product>) {
        viewModelScope.launch(Dispatchers.IO) {
            products.emit(temp)
        }
    }

    fun handleAdd(idProduct: Int): Int {
        productOrder.value!![idProduct]!!.productAmount += 1
        return productOrder.value?.get(idProduct)!!.productAmount
    }

    fun handleSud(idProduct: Int): Int {
        productOrder.value?.get(idProduct)!!.productAmount -= 1
        return productOrder.value?.get(idProduct)!!.productAmount
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("MenuOrderViewModel", "onCleared: ")
    }
}