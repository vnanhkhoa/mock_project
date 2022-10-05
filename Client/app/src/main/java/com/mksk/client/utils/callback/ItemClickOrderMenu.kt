package com.mksk.client.utils.callback

interface ItemClickOrderMenu {
    fun addClick(idProduct: Int): Int
    fun subClick(idProduct: Int): Int
}