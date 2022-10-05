package com.mksk.server.ui.revenue.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mksk.server.data.models.Revenue
import com.mksk.server.data.models.RevenueForDate
import com.mksk.server.databinding.ItemRevenueBinding
import com.mksk.server.utils.BaseDiffUtilCallBack
import com.mksk.server.utils.callback.ItemClick

class RevenueAdapter(private val itemClick: ItemClick<String>) :
    RecyclerView.Adapter<RevenueAdapter.RevenueHolder>() {
    private val revenues = arrayListOf<RevenueForDate>()

    inner class RevenueHolder(private val binding: ItemRevenueBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(revenue: RevenueForDate) {
            binding.apply {
                revenueForDate = revenue
                executePendingBindings()
            }
            itemView.setOnClickListener {
                itemClick.onItemClick(revenue.date)
            }
        }
    }

    fun submitData(temp: List<RevenueForDate>) {
        val diff = DiffUtil.calculateDiff(BaseDiffUtilCallBack(revenues, temp) { io, ine ->
            revenues[io].date == temp[ine].date
        })
        revenues.clear()
        revenues.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RevenueHolder {
        return RevenueHolder(
            ItemRevenueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RevenueHolder, position: Int) {
        holder.bind(revenues[position])
    }

    override fun getItemCount(): Int = revenues.size
}