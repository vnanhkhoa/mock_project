package com.mksk.server.data.models

import android.os.Parcelable
import com.mksk.client.data.models.ProductOrder
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val productID: Int? = null,

    val productName: String,

    val productPrice: Long,

    val productImage: String,
): Parcelable {
    fun toProductOrder(amount: Int = 0): ProductOrder {
        return ProductOrder(productID!!,productName,productPrice,productImage,amount)
    }
}