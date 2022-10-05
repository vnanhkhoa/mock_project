package com.mksk.server.data.database.daos.product

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mksk.server.data.models.Product

@Dao
interface ProductDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: Product): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg t: Product): LongArray

    @Update
    suspend fun update(t: Product)

    @Delete
    suspend fun delete(t: Product)

    @Query("SELECT * FROM product")
    fun getListProduct(): LiveData<List<Product>>
}