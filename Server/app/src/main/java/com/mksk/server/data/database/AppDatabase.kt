package com.mksk.server.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mksk.server.data.database.daos.employee.EmployeeDao
import com.mksk.server.data.database.daos.product.ProductDao
import com.mksk.server.data.database.daos.table.TableDao
import com.mksk.server.data.database.daos.revenue.RevenueDao
import com.mksk.server.data.database.daos.revenuedetail.RevenueDetailDao
import com.mksk.server.data.models.Employee
import com.mksk.server.data.models.Product
import com.mksk.server.data.models.Revenue
import com.mksk.server.data.models.RevenueDetail
import com.mksk.server.utils.Constants.PASSWORD_DEFAULT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.mksk.server.data.models.Table


@Database(
    entities = [Employee::class, Product::class, Revenue::class, RevenueDetail::class,Table::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao
    abstract fun productDao(): ProductDao
    abstract fun tableDao(): TableDao
    abstract fun revenueDao(): RevenueDao
    abstract fun revenueDetailDao(): RevenueDetailDao

    companion object {

        private var INSTANT: AppDatabase? = null

        @Synchronized
        @JvmStatic
        fun getInstant(context: Context): AppDatabase {
            if (INSTANT == null) {
                INSTANT = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "mock_project_server.db"
                ).addCallback(callback).allowMainThreadQueries().build()
            }

            return INSTANT!!
        }

        private val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val employeeDao = INSTANT?.employeeDao()
                val product = INSTANT?.productDao()

                CoroutineScope(Dispatchers.IO).launch {
                    launch {
                        val khoa = Employee(
                            null,
                            "Vo Ngoc Anh Khoa",
                            "37 Doan Nguyen Tuan",
                            PASSWORD_DEFAULT,
                            "0123456789"
                        )
                        val sang = khoa.copy(name = "Bach Xuan Sang", address = "123 Le Van Hien")
                        val khang = khoa.copy(
                            name = "Cao Tho Khang",
                            address = "Nam Ky Khoi Nghia, Ngu Hanh Son"
                        )
                        val mai =
                            khoa.copy(name = "Nguyen Huong Mai", address = "Hoa Vang, Da Nang")
                        employeeDao?.insert(khoa, sang, khang, mai)
                    }

                    launch {
                        val p = Product(
                            productName = "Cafe Sua",
                            productPrice = 15_000L,
                            productImage = "https://product.hstatic.net/1000075078/product/1639377738_ca-phe-sua-da_f25d70c72c5a4c838479b38b38f52278.jpg"
                        )
                        val p1 = p.copy(
                            productName = "Cafe Sua Sai Gon",
                            productPrice = 18_000L,
                            productImage = "https://media3.scdn.vn/img4/2021/05_07/TCbiwM9PRxxKC8dISI3I_simg_de2fe0_500x500_maxb.jpg"
                        )

                        val p2 = p.copy(
                            productName = "Cafe Den",
                            productPrice = 10_000L,
                            productImage = "https://raovat73.com/qlw/prodimg/ca-phe-den-da.jpg"
                        )

                        val p3 = p.copy(
                            productName = "Bac Siu",
                            productPrice = 20_000L,
                            productImage = "http://bingcoffee.vn/wp-content/uploads/2019/11/bac-xi.jpg"
                        )

                        val p4 = p.copy(
                            productName = "Tra Chanh",
                            productPrice = 12_000L,
                            productImage = "https://blog.beemart.vn/wp-content/uploads/2020/05/W1A57nEO14CX_tra-chanh-sa-web.jpg"
                        )

                        val p5 = p.copy(
                            productName = "Tra Dao",
                            productPrice = 22_000L,
                            productImage = "https://cafevu.vn/wp-content/uploads/2021/05/tra-dao-cam-sa.jpg"
                        )

                        product?.insert(p, p1, p2, p3, p4, p5)
                    }
                }
            }
        }

    }
}