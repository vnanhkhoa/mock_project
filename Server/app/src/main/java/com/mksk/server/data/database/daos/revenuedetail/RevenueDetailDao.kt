package com.mksk.server.data.database.daos.revenuedetail

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mksk.server.data.models.RevenueDetail
import com.mksk.server.data.models.RevenueForProduct

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

    @Query("select p._productName as 'productName',p._productPrice as 'productPrice',p._productImage as 'productImg', sum(rd._amount) as 'amount' from product as p,revenue as r ,revenuedetail as rd where r.id = rd._id_revenue and rd._id_product = p.productID and r._date = :date group by p.productID")
    fun getListProductForDate(date: String): LiveData<List<RevenueForProduct>>
}