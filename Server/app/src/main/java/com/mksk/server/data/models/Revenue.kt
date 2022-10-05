package com.mksk.server.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity
data class Revenue(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "_employeeId")
    val employeeID: Int,

    @ColumnInfo(name = "_tableId")
    val tableId: Int,

    @ColumnInfo(name = "_date")
    val date: String? = null,

    @ColumnInfo(name = "_totalMoney")
    val totalMoney: Long,
) : Parcelable {
    init {
        date ?: dayNowFormat()
    }

    private fun dayNowFormat(): String {
        val now = Calendar.getInstance()
        val day = "0${now.get(Calendar.DAY_OF_MONTH)}".takeLast(2)
        val month = "0${now.get(Calendar.MONTH) + 1}".takeLast(2)
        val year = now.get(Calendar.YEAR)

        return "$day/$month/$year"
    }

}
