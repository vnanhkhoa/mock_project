package com.mksk.client.data.database.daos.revenuedetail

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mksk.server.data.models.RevenueDetail

@Dao
interface RevenueDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: RevenueDetail): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg t: RevenueDetail): LongArray

    @Update
    suspend fun update(t: RevenueDetail)

    @Delete
    suspend fun delete(t: RevenueDetail)

    @Query("Delete from order_detail where _id_product =:id and _id_revenue = :idR")
    suspend fun deleteByIdProduct(id: Int, idR: Int)

    @Query("Update order_detail SET _amount = :amount where _id_product = :idP and _id_revenue = :idRevenueDetail")
    suspend fun updateAmount(idRevenueDetail: Int, idP: Int, amount: Int)

    @Query("Delete from order_detail where _id_revenue = :id")
    suspend fun deleteByIdRevenue(id: Int)
}