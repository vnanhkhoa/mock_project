package com.mksk.server.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productID: Int? = null,

    @ColumnInfo(name = "_productName")
    val productName: String,

    @ColumnInfo(name = "_productPrice")
    val productPrice: Long,

    @ColumnInfo(name = "_productImage")
    val productImage: String,
): Parcelable