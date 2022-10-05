package com.mksk.server.ui.revenuedetail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mksk.server.data.models.RevenueDetail
import com.mksk.server.data.models.RevenueForProduct
import com.mksk.server.databinding.ItemRevenueDetailBinding
import com.mksk.server.utils.BaseDiffUtilCallBack

class RevenueDetailAdapter :
    RecyclerView.Adapter<RevenueDetailAdapter.RevenueDetailHolder>() {
    private val revenueDetails = arrayListOf<RevenueForProduct>()

    inner class RevenueDetailHolder(private val binding: ItemRevenueDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(revenueDetail: RevenueForProduct) {
            binding.apply {
                rfp = revenueDetail
                executePendingBindings()
            }
        }
    }

    fun submitData(temp: List<RevenueForProduct>) {
        val diff = DiffUtil.calculateDiff(BaseDiffUtilCallBack(revenueDetails, temp) { old, new ->
            revenueDetails[old].productName == temp[new].productName
        })
        revenueDetails.clear()
        revenueDetails.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RevenueDetailHolder {
        return RevenueDetailHolder(
            ItemRevenueDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RevenueDetailHolder, position: Int) {
        holder.bind(revenueDetails[position])
    }

    override fun getItemCount(): Int = revenueDetails.size
}