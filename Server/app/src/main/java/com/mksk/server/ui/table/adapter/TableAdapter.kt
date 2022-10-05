package com.mksk.server.ui.table.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mksk.server.data.models.Table
import com.mksk.server.databinding.ItemTableBinding
import com.mksk.server.utils.BaseDiffUtilCallBack
import com.mksk.server.utils.callback.ItemClick

class TableAdapter(private var itemClick: ItemClick<Table>) :
    RecyclerView.Adapter<TableAdapter.ViewHolder>() {
    private var table = arrayListOf<Table>()

    inner class ViewHolder(private val binding: ItemTableBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(temp: Table) {
            binding.apply {
                t = temp
                click = itemClick
                executePendingBindings()
            }
        }
    }

    fun submitData(temp: List<Table>) {
        val diff = DiffUtil.calculateDiff(BaseDiffUtilCallBack(table, temp) { io, ine ->
            table[io].id == temp[ine].id
        })
        table.clear()
        table.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        table[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = table.size

}