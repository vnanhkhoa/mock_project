package com.mksk.client.data.source

import android.content.Context
import androidx.lifecycle.LiveData
import com.mksk.client.data.database.AppDatabase
import com.mksk.client.data.database.daos.revenue.RevenueRepository
import com.mksk.client.data.database.daos.revenuedetail.RevenueDetailRepository
import com.mksk.client.data.models.RevenueWithRevenueDetail
import com.mksk.client.data.source.interf.Repository
import com.mksk.server.data.models.Revenue
import com.mksk.server.data.models.RevenueDetail
import kotlinx.coroutines.flow.Flow

class AppRepository(db: AppDatabase) : Repository {

    private val revenueDao = RevenueRepository.getInstant(db.revenueDao())
    private val revenueDetailDao = RevenueDetailRepository.getInstant(db.revenueDetailDao())

    companion object {
        private var INSTANT: AppRepository? = null

        @JvmStatic
        fun getInstant(context: Context): AppRepository {
            if (INSTANT == null) {
                val db = AppDatabase.getInstant(context)
                INSTANT = AppRepository(db)
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

    override suspend fun insert(t: RevenueDetail): Long {
        return revenueDetailDao.insert(t)
    }

    override suspend fun insert(vararg t: RevenueDetail): LongArray {
        return revenueDetailDao.insert(*t)
    }

    override suspend fun update(t: Revenue) {
        revenueDao.update(t)
    }

    override suspend fun update(t: RevenueDetail) {
        revenueDetailDao.update(t)
    }

    override suspend fun delete(t: Revenue) {
        revenueDao.delete(t)
    }

    override suspend fun delete(t: RevenueDetail) {
        revenueDetailDao.delete(t)
    }

    override fun getListRevenueByTableId(id: Int): LiveData<RevenueWithRevenueDetail> {
        return revenueDao.getListRevenueByTableId(id)
    }

    override fun getTableIsOrdering(): Flow<List<Int>> {
        return revenueDao.getTableIsOrdering()
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