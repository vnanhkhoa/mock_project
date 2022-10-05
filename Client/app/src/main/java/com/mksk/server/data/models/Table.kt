package com.mksk.server.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Table(
    val id: Int? = null,

    val name: Int,

    val status: Boolean = false
) : Parcelable {
    override fun toString(): String = "$name"
}
