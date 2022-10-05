package com.mksk.server.ui.revenue

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mksk.server.data.models.Product
import com.mksk.server.data.models.Revenue
import com.mksk.server.data.models.RevenueDetail
import com.mksk.server.data.source.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RevenueViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository.getInstant(application.applicationContext)

    val revenueForDates = repository.getListRevenueForDate()

    val products = repository.getListProduct()

    fun setData(list: List<Product>) {
        viewModelScope.launch(Dispatchers.IO) {
            repeat(100) {
                launch {
                    val date = "0${(1..30).random()}".takeLast(2)
                    val nProduct = (2..5).random()
                    val orders = (0..nProduct).map {
                        val p = list.random()
                        RevenueDetail(
                            null,
                            p.productID!!,
                            p.productPrice.toInt(),
                            (1..4).random()
                        )
                    }

                    val idBill = async {
                        repository.insert(
                            Revenue(
                                employeeID = (1..4).random(),
                                tableId = (1..15).random(),
                                date = "$date/09/2022",
                                totalMoney = orders.sumOf { (it.amount * it.idRevenue).toLong() }
                            )
                        )
                    }

                    val revenueDetail = orders.map {
                        it.copy(idRevenue = idBill.await().toInt())
                    }

                    repository.insert(*revenueDetail.toTypedArray())

                }
            }
        }
    }

}