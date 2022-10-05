package com.mksk.server.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "_name")
    val name: String,

    @ColumnInfo(name = "_address")
    val address: String,

    @ColumnInfo(name = "_password")
    val password: String,

    @ColumnInfo(name = "_phone_number")
    val phoneNumber: String,
):Serializable, Parcelable
