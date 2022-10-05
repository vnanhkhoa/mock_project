package com.mksk.server.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class RevenueDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "_id_product")
    val idProduct: Int,

    @ColumnInfo(name = "_id_revenue")
    val idRevenue: Int,

    @ColumnInfo(name = "_amount")
    val amount: Int,
) : Parcelable
