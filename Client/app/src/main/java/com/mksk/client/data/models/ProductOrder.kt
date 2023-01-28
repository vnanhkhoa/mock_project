package com.mksk.client.data.models

import android.os.Parcelable
import com.mksk.server.data.models.RevenueDetail
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductOrder(
    val productID: Int,
    val productName: String,
    val productPrice: Long,
    val productImage: String,
    var productAmount: Int = 0,
):Parcelable {
    fun toRevenueDetail(id: Int): RevenueDetail {
        return RevenueDetail(idProduct = productID, idRevenue = id, amount = productAmount)
    }
}