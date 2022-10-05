package com.mksk.server.ui.employee.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mksk.server.data.models.Employee
import com.mksk.server.databinding.ItemEmployeeListBinding
import com.mksk.server.utils.BaseDiffUtilCallBack
import com.mksk.server.utils.callback.ItemClick

class EmployeeAdapter(private val itemClick: ItemClick<Employee>) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {
    private var employee = arrayListOf<Employee>()

    inner class ViewHolder(private val binding: ItemEmployeeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(e: Employee) {
            binding.apply {
                employee = e
                itemClick = this@EmployeeAdapter.itemClick
                executePendingBindings()
            }
        }

    }

    fun submitData(temp: List<Employee>) {
        val diff = DiffUtil.calculateDiff(BaseDiffUtilCallBack(employee, temp) { io, ine ->
            employee[io].id == temp[ine].id
        })
        employee.clear()
        employee.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEmployeeListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        employee[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = employee.size
}