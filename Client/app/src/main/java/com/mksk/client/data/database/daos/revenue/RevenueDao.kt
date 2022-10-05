package com.mksk.client.data.database.daos.revenue

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mksk.client.data.models.RevenueWithRevenueDetail
import com.mksk.server.data.models.Revenue
import kotlinx.coroutines.flow.Flow

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

    @Transaction
    @Query("SELECT * FROM `order` where _tableId = :id ")
    fun getListRevenueByTableId(id: Int): LiveData<RevenueWithRevenueDetail>

    @Query("SELECT _tableId FROM `order`")
    fun getTableIsOrdering(): Flow<List<Int>>
}
