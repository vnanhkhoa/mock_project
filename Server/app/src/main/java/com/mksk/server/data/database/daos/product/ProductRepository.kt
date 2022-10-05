package com.mksk.server.data.database.daos.product
import androidx.lifecycle.LiveData
import com.mksk.server.data.models.Product

class ProductRepository(private val productDao: ProductDao): ProductDao {

    companion object {
        private var INSTANT: ProductRepository? = null

        fun getInstant(productDao: ProductDao): ProductRepository {
            if (INSTANT == null) {
                INSTANT = ProductRepository(productDao)
            }

            return INSTANT!!
        }
    }

    override fun getListProduct(): LiveData<List<Product>> {
        return productDao.getListProduct()
    }

    override suspend fun insert(t: Product): Long {
        return productDao.insert(t)
    }

    override suspend fun insert(vararg t: Product): LongArray {
        return productDao.insert(*t)
    }

    override suspend fun update(t: Product) {
       productDao.update(t)
    }

    override suspend fun delete(t: Product) {
        productDao.delete(t)
    }

}