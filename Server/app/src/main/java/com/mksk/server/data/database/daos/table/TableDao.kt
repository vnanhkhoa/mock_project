package com.mksk.server.data.database.daos.table

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mksk.server.data.models.Table

@Dao
interface TableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: Table): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg t: Table): LongArray

    @Update
    suspend fun update(t: Table)

    @Delete
    suspend fun delete(t: Table)

    @Query("SELECT * FROM `Table` ORDER BY _name ASC")
    fun getListTable(): LiveData<List<Table>>

    @Query("SELECT * FROM `Table` ORDER BY _name DESC LIMIT 1")
    fun getLastRecord(): LiveData<Table>
}