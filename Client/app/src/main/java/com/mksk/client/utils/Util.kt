package com.mksk.client.utils

import java.text.NumberFormat
import java.util.*

fun getDateCurrent(): String {
    val now = Calendar.getInstance()
    val day = "0${now.get(Calendar.DAY_OF_MONTH)}".takeLast(2)
    val month = "0${now.get(Calendar.MONTH) + 1}".takeLast(2)
    val year = now.get(Calendar.YEAR)

    return "$day/$month/$year"
}

fun getTimeCurrent(): String {
    val now = Calendar.getInstance()
    val hour = "0${now.get(Calendar.HOUR_OF_DAY)}".takeLast(2)
    val second = "0${now.get(Calendar.MINUTE)}".takeLast(2)

    return "$hour:$second"
}

fun <T> merge(first: List<T>, second: List<T>): List<T> {
    val list: MutableList<T> = ArrayList(first)
    list.addAll(second)
    return list
}

fun formatNumber(): NumberFormat {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("VND")

    return format
}