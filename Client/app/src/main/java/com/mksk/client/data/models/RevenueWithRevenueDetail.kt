package com.mksk.client.data.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.mksk.server.data.models.Revenue
import com.mksk.server.data.models.RevenueDetail
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class RevenueWithRevenueDetail(
    @Embedded val revenue: Revenue,

    @Relation(
        parentColumn = "id",
        entityColumn = "_id_revenue"
    )
    val listRevenueDetail: List<RevenueDetail>
) : Serializable,Parcelable
