package com.mksk.server.data.database.daos.revenue

import androidx.lifecycle.LiveData
import com.mksk.server.data.models.Revenue
import com.mksk.server.data.models.RevenueForDate

class RevenueRepository(private val revenueDao: RevenueDao): RevenueDao {

    companion object {
        private var INSTANT: RevenueRepository? = null

        fun getInstant(revenueDao: RevenueDao): RevenueRepository {
            if (INSTANT == null) {
                INSTANT = RevenueRepository(revenueDao)
            }

            return INSTANT!!
        }
    }

    override suspend fun insert(t: Revenue): Long {
        return revenueDao.insert(t)
    }

    override suspend fun insert(vararg t: Revenue): LongArray {
        return revenueDao.insert(*t)
    }

    override suspend fun update(t: Revenue) {
        revenueDao.update(t)
    }

    override suspend fun delete(t: Revenue) {
        revenueDao.delete(t)
    }

    override fun getListRevenueForDate(): LiveData<List<RevenueForDate>> {
        return revenueDao.getListRevenueForDate()
    }
}