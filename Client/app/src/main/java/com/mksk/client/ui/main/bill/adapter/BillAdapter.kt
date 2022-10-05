package com.mksk.client.ui.main.bill.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mksk.client.data.models.ProductOrder
import com.mksk.client.databinding.ItemBillBinding
import com.mksk.client.databinding.ItemMenuPayBinding
import com.mksk.client.utils.BaseDiffUtilCallBack

class BillAdapter:RecyclerView.Adapter<BillAdapter.ViewHolder>() {
    private var products = arrayListOf<ProductOrder>()

    class ViewHolder(val binding: ItemBillBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductOrder) {
            binding.apply {
                bill = product
                executePendingBindings()
            }
        }
    }

    fun submitData(temp: List<ProductOrder>) {
        val diff = DiffUtil.calculateDiff(BaseDiffUtilCallBack(products, temp) { io, ine ->
            products[io].productID == temp[ine].productID
        })
        products.clear()
        products.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}