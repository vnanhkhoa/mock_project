package com.mksk.client.ui.main

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mksk.client.R
import com.mksk.client.databinding.ActivityMainBinding
import com.mksk.client.databinding.NavHeaderMainBinding
import com.mksk.server.ServerAIDL
import com.mksk.server.data.models.Employee
import com.mksk.server.data.models.Product
import com.mksk.server.data.models.Table

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    companion object {
        const val EMPLOYEE = "EMPLOYEE"
        const val PRODUCT = "PRODUCT"
        const val TABLE = "TABLE"

        const val ACTION_EMPLOYEE = "com.mksk.client.EMPLOYEE"
        const val ACTION_PRODUCT = "com.mksk.client.PRODUCT"
        const val ACTION_TABLE = "com.mksk.client.TABLE"
    }

    private val mapAction = mapOf(
        EMPLOYEE to ACTION_EMPLOYEE,
        PRODUCT to ACTION_PRODUCT,
        TABLE to ACTION_TABLE
    )

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var mService: ServerAIDL
    private val viewModel: MainViewModel by viewModels()
    private var isConnect: Boolean = false

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mService = ServerAIDL.Stub.asInterface(p1)
            isConnect = true

            viewModel.setService(mService)
            mService.sendAction(mapAction)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isConnect = false
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            p1?.run {
                val list = getParcelableArrayListExtra<Parcelable>("DATA_CHANGE")
                when (action) {
                    ACTION_EMPLOYEE -> {
                        viewModel.setListEmployee(list as List<Employee>)
                    }
                    ACTION_TABLE -> {
                        viewModel.setListTable(list as List<Table>)
                    }
                    ACTION_PRODUCT -> {
                        viewModel.setListProduct(list as List<Product>)
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        appBarConfig = AppBarConfiguration(
            setOf(R.id.welcomeFragment, R.id.loginFragment, R.id.tableFragment),
            binding.drawerLayout
        )

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        navController.addOnDestinationChangedListener { _, des, _ ->
            binding.apply {
                when (des.id) {
                    R.id.welcomeFragment, R.id.loginFragment -> {
                        toolbar.visibility = View.GONE
                        drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                    }
                    R.id.tableFragment -> {
                        toolbar.visibility = View.VISIBLE
                        drawerLayout.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                    }
                    else -> {
                        toolbar.visibility = View.VISIBLE
                        drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                    }
                }

                if (des.id == R.id.billFragment) {
                    toolbar.navigationIcon = null
                } else {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }

        }
        setupActionBarWithNavController(navController, appBarConfig)
        binding.navView.setupWithNavController(navController)

        viewModel.employee.observe(this) {
            val header = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
            header.apply {
                tvName.text = it.name
            }
            mService.updateEmployee(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_PRODUCT)
        intentFilter.addAction(ACTION_TABLE)
        intentFilter.addAction(ACTION_EMPLOYEE)
        registerReceiver(receiver, intentFilter)
        Intent("com.mksk.server.RemoteService").apply {
            setPackage("com.mksk.server")
            bindService(this, mServiceConnection, BIND_AUTO_CREATE)
        }

    }
}