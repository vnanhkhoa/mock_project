package com.mksk.server.service


import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.mksk.server.ServerAIDL
import com.mksk.server.data.models.*
import com.mksk.server.data.source.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RemoteService : LifecycleService() {

    companion object {
        const val EMPLOYEE = "EMPLOYEE"
        const val PRODUCT = "PRODUCT"
        const val TABLE = "TABLE"
    }

    private lateinit var repository: AppRepository
    private val actionSend = MutableLiveData<Map<String, String>>()

    private val binder = object : ServerAIDL.Stub() {
        override fun sendAction(action: Map<String, String>?) {
            actionSend.postValue(action!!)
        }

        override fun sendRevenue(revenue: Revenue?, revenueDetails: List<RevenueDetail>?) {
            lifecycleScope.launch {
                val id = withContext(Dispatchers.Default) {
                    repository.insert(revenue!!.copy(id = null))
                }

                val r = revenueDetails!!.map {
                    it.copy(
                        id = null,
                        idRevenue = id.toInt()
                    )
                }

                repository.insert(*r.toTypedArray())
            }
        }

        override fun updateEmployee(e: Employee?) {
            lifecycleScope.launch {
                if (e != null) {
                    repository.update(e)
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        repository = AppRepository.getInstant(this)
        actionSend.observe(this) { ac ->
            repository.apply {
                getListProduct().observe(this@RemoteService) {
                    Intent(ac[PRODUCT]).apply {
                        putParcelableArrayListExtra("DATA_CHANGE", it as ArrayList<Product>)
                        sendBroadcast(this)
                    }
                }

                getListTable().observe(this@RemoteService) {
                    Intent(ac[TABLE]).apply {
                        putParcelableArrayListExtra("DATA_CHANGE", it as ArrayList<Table>)
                        sendBroadcast(this)
                    }
                }

                getInfo().observe(this@RemoteService) {
                    Intent(ac[EMPLOYEE]).apply {
                        putParcelableArrayListExtra("DATA_CHANGE", it as ArrayList<Employee>)
                        sendBroadcast(this)
                    }
                }
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }
}