package com.mksk.server.data.source

import android.content.Context
import androidx.lifecycle.LiveData
import com.mksk.server.data.database.AppDatabase
import com.mksk.server.data.database.daos.employee.EmployeeRepository
import com.mksk.server.data.database.daos.product.ProductRepository
import com.mksk.server.data.database.daos.revenue.RevenueRepository
import com.mksk.server.data.database.daos.revenuedetail.RevenueDetailRepository
import com.mksk.server.data.database.daos.table.TableRepository
import com.mksk.server.data.models.*
import com.mksk.server.data.source.interf.Repository

class AppRepository(db: AppDatabase) : Repository {

    private val employeeRepository = EmployeeRepository.getInstant(db.employeeDao())
    private val productRepository = ProductRepository.getInstant(db.productDao())
    private val revenueRepository = RevenueRepository.getInstant(db.revenueDao())
    private val revenueDetailRepository = RevenueDetailRepository.getInstant(db.revenueDetailDao())
    private val tableRepository =TableRepository.getInstant(db.tableDao())

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

    override suspend fun insert(t: Employee): Long {
        return employeeRepository.insert(t)
    }

    override suspend fun insert(vararg t: Employee): LongArray {
        return employeeRepository.insert(*t)
    }

    override suspend fun insert(t: Product): Long {
        return productRepository.insert(t)
    }

    override suspend fun insert(vararg t: Product): LongArray {
        return productRepository.insert(*t)
    }

    override suspend fun insert(t: Revenue): Long {
        return revenueRepository.insert(t)
    }

    override suspend fun insert(vararg t: Revenue): LongArray {
        return revenueRepository.insert(*t)
    }

    override suspend fun insert(t: RevenueDetail): Long {
        return revenueDetailRepository.insert(t)
    }

    override suspend fun insert(vararg t: RevenueDetail): LongArray {
        return revenueDetailRepository.insert(*t)
    }

    override suspend fun insert(t: Table): Long {
        return tableRepository.insert(t)
    }

    override suspend fun insert(vararg t: Table): LongArray {
        return tableRepository.insert(*t)
    }

    override suspend fun update(t: Employee) {
        employeeRepository.update(t)
    }

    override suspend fun update(t: Product) {
        productRepository.update(t)
    }

    override suspend fun update(t: Revenue) {
        revenueRepository.update(t)
    }

    override suspend fun update(t: RevenueDetail) {
        revenueDetailRepository.update(t)
    }

    override suspend fun update(t: Table) {
        tableRepository.update(t)
    }

    override suspend fun delete(t: Employee) {
        employeeRepository.delete(t)
    }

    override suspend fun delete(t: Product) {
        productRepository.delete(t)
    }

    override suspend fun delete(t: Revenue) {
        revenueRepository.delete(t)
    }

    override suspend fun delete(t: RevenueDetail) {
        revenueDetailRepository.delete(t)
    }

    override suspend fun delete(t: Table) {
        tableRepository.delete(t)
    }

    override fun getInfo(): LiveData<List<Employee>> {
        return employeeRepository.getInfo()
    }

    override fun getListProduct(): LiveData<List<Product>> {
        return productRepository.getListProduct()
    }

    override fun getListRevenueForDate(): LiveData<List<RevenueForDate>> {
        return revenueRepository.getListRevenueForDate()
    }

    override fun getListProductForDate(date: String): LiveData<List<RevenueForProduct>> {
        return revenueDetailRepository.getListProductForDate(date)
    }

    override fun getListTable(): LiveData<List<Table>> {
        return tableRepository.getListTable()
    }

    override fun getLastRecord(): LiveData<Table> {
        return tableRepository.getLastRecord()
    }

}