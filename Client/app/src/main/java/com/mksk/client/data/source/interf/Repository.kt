package com.mksk.client.data.source.interf

import com.mksk.client.data.database.daos.revenue.RevenueDao
import com.mksk.client.data.database.daos.revenuedetail.RevenueDetailDao

interface Repository: RevenueDao, RevenueDetailDao {
}