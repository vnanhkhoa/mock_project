package com.mksk.client.ui.main.table.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mksk.client.databinding.ItemTableBinding
import com.mksk.client.utils.BaseDiffUtilCallBack
import com.mksk.server.data.models.Table
import com.mksk.server.utils.callback.ItemClick

class TableAdapter(private val itemClick: ItemClick<Table>) :
    RecyclerView.Adapter<TableAdapter.TableHolder>() {
    private val tables = arrayListOf<Table>()

    inner class TableHolder(private val binding: ItemTableBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(table: Table) {
            binding.table = table
            binding.itemClick = itemClick
            binding.executePendingBindings()
        }
    }

    fun submitData(temp: List<Table>) {
        val diff = DiffUtil.calculateDiff(BaseDiffUtilCallBack(tables, temp) { io, ine ->
            tables[io].id == temp[ine].id
        })
        tables.clear()
        tables.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableHolder {
        return TableHolder(
            ItemTableBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TableHolder, position: Int) {
        tables[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = tables.size
}
