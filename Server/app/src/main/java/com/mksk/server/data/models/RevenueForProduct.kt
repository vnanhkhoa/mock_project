package com.mksk.server.data.models

data class RevenueForProduct(
    val productName: String,
    val productImg: String,
    val productPrice: Long,
    val amount: Int,
) {
    fun total() : Long = amount * productPrice
}
