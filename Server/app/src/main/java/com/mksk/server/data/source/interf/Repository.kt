package com.mksk.server.data.source.interf

import com.mksk.server.data.database.daos.employee.EmployeeDao
import com.mksk.server.data.database.daos.product.ProductDao
import com.mksk.server.data.database.daos.revenue.RevenueDao
import com.mksk.server.data.database.daos.revenuedetail.RevenueDetailDao
import com.mksk.server.data.database.daos.table.TableDao

interface Repository: EmployeeDao, ProductDao,RevenueDao,RevenueDetailDao,TableDao