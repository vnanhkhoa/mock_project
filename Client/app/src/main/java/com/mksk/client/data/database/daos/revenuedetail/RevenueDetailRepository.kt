package com.mksk.client.data.database.daos.revenuedetail

import androidx.lifecycle.LiveData
import com.mksk.server.data.models.RevenueDetail

class RevenueDetailRepository(private val revenueDetailDao: RevenueDetailDao): RevenueDetailDao {

    companion object {
        private var INSTANT: RevenueDetailRepository? = null

        fun getInstant(revenueDetailDao: RevenueDetailDao): RevenueDetailRepository {
            if (INSTANT == null) {
                INSTANT = RevenueDetailRepository(revenueDetailDao)
            }

            return INSTANT!!
        }
    }

    override suspend fun insert(t: RevenueDetail): Long {
        return revenueDetailDao.insert(t)
    }

    override suspend fun insert(vararg t: RevenueDetail): LongArray {
        return revenueDetailDao.insert(*t)
    }

    override suspend fun update(t: RevenueDetail) {
        revenueDetailDao.update(t)
    }

    override suspend fun delete(t: RevenueDetail) {
        revenueDetailDao.delete(t)
    }

    override suspend fun deleteByIdProduct(id: Int, idR: Int) {
        revenueDetailDao.deleteByIdProduct(id,idR)
    }

    override suspend fun updateAmount(idRevenueDetail: Int, idP: Int, amount: Int) {
        revenueDetailDao.updateAmount(idRevenueDetail,idP,amount)
    }

    override suspend fun deleteByIdRevenue(id: Int) {
        revenueDetailDao.deleteByIdRevenue(id)
    }
}