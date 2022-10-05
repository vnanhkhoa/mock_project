package com.mksk.server.ui.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mksk.server.data.models.Product
import com.mksk.server.databinding.ItemMenuBinding
import com.mksk.server.utils.BaseDiffUtilCallBack
import com.mksk.server.utils.callback.ItemClick

class ProductAdapter(private var itemClick: ItemClick<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private val products = arrayListOf<Product>()

    inner class ViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                p = product
                click = itemClick
                executePendingBindings()
            }
        }
    }

    fun setData(data: List<Product>) {
        val diff = DiffUtil.calculateDiff(BaseDiffUtilCallBack(products, data) { io, ine ->
            products[io].productID == data[ine].productID
        })
        products.clear()
        products.addAll(data)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}