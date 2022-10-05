package com.mksk.server.data.database.daos.employee

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mksk.server.data.models.Employee

@Dao
interface EmployeeDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: Employee): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg t: Employee): LongArray

    @Update
    suspend fun update(t: Employee)

    @Delete
    suspend fun delete(t: Employee)

    @Query("SELECT * FROM `Employee`")
    fun getInfo():LiveData<List<Employee>>
}