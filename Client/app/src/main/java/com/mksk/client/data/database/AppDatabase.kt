package com.mksk.client.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mksk.client.data.database.daos.revenue.RevenueDao
import com.mksk.client.data.database.daos.revenuedetail.RevenueDetailDao
import com.mksk.server.data.models.Revenue
import com.mksk.server.data.models.RevenueDetail

@Database(entities = [Revenue::class,RevenueDetail::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

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
                    "mock_project_client.db"
                ).allowMainThreadQueries().build()
            }

            return INSTANT!!
        }

    }
}