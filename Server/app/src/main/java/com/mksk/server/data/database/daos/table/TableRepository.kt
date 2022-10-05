package com.mksk.server.data.database.daos.table

import androidx.lifecycle.LiveData
import com.mksk.server.data.models.Table

class TableRepository(private val tableDao: TableDao) : TableDao {
    companion object {
        private var INSTANT: TableRepository? = null

        fun getInstant(tableDao: TableDao): TableRepository {
            if (INSTANT == null) {
                INSTANT = TableRepository(tableDao)
            }

            return INSTANT!!
        }
    }

    override suspend fun insert(t: Table): Long {
        return tableDao.insert(t)
    }

    override suspend fun insert(vararg t: Table): LongArray {
        return tableDao.insert(*t)
    }

    override suspend fun update(t: Table) {
        tableDao.update(t)
    }

    override suspend fun delete(t: Table) {
        tableDao.delete(t)
    }

    override fun getListTable(): LiveData<List<Table>> {
        return tableDao.getListTable()
    }

    override fun getLastRecord(): LiveData<Table> {
        return tableDao.getLastRecord()
    }
}