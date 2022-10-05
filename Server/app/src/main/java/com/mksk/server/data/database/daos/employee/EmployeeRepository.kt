package com.mksk.server.data.database.daos.employee

import androidx.lifecycle.LiveData
import com.mksk.server.data.models.Employee

class EmployeeRepository(private val employeeDao: EmployeeDao) : EmployeeDao{

    companion object {
        private var INSTANT: EmployeeRepository? = null

        fun getInstant(employeeDao: EmployeeDao): EmployeeRepository {
            if (INSTANT == null) {
                INSTANT = EmployeeRepository(employeeDao)
            }

            return INSTANT!!
        }
    }

    override fun getInfo(): LiveData<List<Employee>> = employeeDao.getInfo()


    override suspend fun insert(t: Employee): Long {
        return employeeDao.insert(t)
    }

    override suspend fun insert(vararg t: Employee): LongArray {
        return employeeDao.insert(*t)
    }

    override suspend fun update(t: Employee) {
        employeeDao.update(t)
    }

    override suspend fun delete(t: Employee) {
        employeeDao.delete(t)
    }

}