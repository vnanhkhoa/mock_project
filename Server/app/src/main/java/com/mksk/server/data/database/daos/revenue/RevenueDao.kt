package com.mksk.server.data.database.daos.revenue

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mksk.server.data.models.Revenue
import com.mksk.server.data.models.RevenueForDate

@Dao
interface RevenueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: Revenue): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg t: Revenue): LongArray

    @Update
    suspend fun update(t: Revenue)

    @Delete
    suspend fun delete(t: Revenue)

    @Query("select _date as 'date', count(id) as 'count', sum(_totalMoney) as 'total' from revenue group by _date")
    fun getListRevenueForDate(): LiveData<List<RevenueForDate>>
}