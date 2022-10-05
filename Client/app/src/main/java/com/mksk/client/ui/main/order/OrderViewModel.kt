package com.mksk.client.ui.main.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mksk.client.data.models.RevenueWithRevenueDetail
import com.mksk.client.data.source.AppRepository
import com.mksk.server.data.models.Product
import com.mksk.server.data.models.Revenue
import com.mksk.server.data.models.RevenueDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository.getInstant(application.applicationContext)

    private lateinit var revenueWithRevenueDetail: RevenueWithRevenueDetail

    private val products = MutableStateFlow(listOf<Product>())
    private val listRD = MutableStateFlow(listOf<RevenueDetail>())
    private val _productOrders = listRD.combine(products) { revenueDetail, products ->
        revenueDetail.associate {
            val p = products.find { pr -> pr.productID == it.idProduct }
            it.idProduct to p!!.toProductOrder(it.amount)
        }
    }
    val productOrders = liveData {
        _productOrders.collect {
            emit(it)
        }
    }

    suspend fun insertRevenue(revenue: Revenue): Long {
        return repository.insert(revenue)
    }

    fun insertRevenueDetail(vararg revenueDetail: RevenueDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(*revenueDetail)
        }
    }

    fun getOrderByTableId(id: Int): LiveData<RevenueWithRevenueDetail> {
        return repository.getListRevenueByTableId(id)
    }

    fun setListProduct(temp: List<Product>) {
        viewModelScope.launch(Dispatchers.IO) {
            products.emit(temp)
        }
    }

    fun setListRD(temp: List<RevenueDetail>) {
        viewModelScope.launch(Dispatchers.IO) {
            listRD.emit(temp)
        }
    }

    fun getListRD() = listRD.value

    fun setRevenueWithRevenueDetail(revenue: RevenueWithRevenueDetail) {
        this.revenueWithRevenueDetail = revenue
    }

    fun getRevenueWithRevenueDetail() = revenueWithRevenueDetail

    fun handleAdd(idProduct: Int): Int {
        var amount = 0
        productOrders.value?.run {
            this[idProduct]?.let {
                amount = it.productAmount
                revenueWithRevenueDetail.apply {
                    viewModelScope.launch {
                        repository.updateAmount(revenue.id!!, idProduct, amount + 1)
                        repository.update(revenue.copy(totalMoney = revenue.totalMoney + it.productPrice))
                    }
                }
            }
        }
        return amount
    }

    fun handleSud(idProduct: Int): Int {
        var amount = 0
        productOrders.value?.run {
            this[idProduct]?.let {
                amount = it.productAmount
                viewModelScope.launch {
                    val a = amount - 1
                    revenueWithRevenueDetail.apply {
                        if (a <= 0) {
                            repository.deleteByIdProduct(idProduct, revenue.id!!)
                        } else {
                            repository.updateAmount(revenue.id!!, idProduct, a)
                        }
                        repository.update(revenue.copy(totalMoney = revenue.totalMoney - it.productPrice))
                    }
                }
            }
        }
        return amount
    }
}