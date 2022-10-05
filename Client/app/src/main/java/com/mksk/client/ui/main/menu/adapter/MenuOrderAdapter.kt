package com.mksk.client.ui.main.menu.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mksk.client.R
import com.mksk.client.data.models.ProductOrder
import com.mksk.client.databinding.ItemMenuOrderBinding
import com.mksk.client.utils.BaseDiffUtilCallBack
import com.mksk.client.utils.callback.ItemClickOrderMenu

class MenuOrderAdapter(private val listener: ItemClickOrderMenu) :
    RecyclerView.Adapter<MenuOrderAdapter.MenuOrderHolder>() {
    private var products = mutableMapOf<Int, ProductOrder>()

    inner class MenuOrderHolder(private val binding: ItemMenuOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: ProductOrder) {
            binding.apply {

                product.apply {
                    tvProductName.text = productName
                    tvProductPrice.text = String.format("%,3d", productPrice) + 'đ'
                    tvNumberProduct.text = "$productAmount"
                }

                imgBtnPlus.setOnClickListener {
                    val result = listener.addClick(product.productID)
                    product.productAmount = result
                    updateAmount(result)
                }

                imgBtnSub.setOnClickListener {
                    if (product.productAmount == 0) return@setOnClickListener
                    val result = listener.subClick(product.productID)
                    product.productAmount = result
                    updateAmount(result)
                }

                Glide.with(binding.root)
                    .load(product.productImage)
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_round_error_24)
                    .into(imgCoffee)
            }
        }

        private fun updateAmount(amount: Int) {
            binding.tvNumberProduct.text = "$amount"
        }
    }

    fun submitData(temp: Map<Int, ProductOrder>) {
        val diff = DiffUtil.calculateDiff(
            BaseDiffUtilCallBack(
                products.keys.toList(),
                temp.keys.toList()
            ) { io, ine ->
                products.keys.toList()[io] == temp.keys.toList()[ine]
            })
        products.clear()
        products.putAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuOrderHolder {
        return MenuOrderHolder(
            ItemMenuOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MenuOrderHolder, position: Int) {
        holder.bind(products.values.toList()[position])
    }

    override fun getItemCount(): Int = products.size
}