package com.mksk.server.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Employee(
    val id: Int? = null,

    val name: String,

    val address: String,

    val password: String,

    val phoneNumber: String,
) : Serializable, Parcelable {
    override fun toString(): String = "$name - $phoneNumber"
}
