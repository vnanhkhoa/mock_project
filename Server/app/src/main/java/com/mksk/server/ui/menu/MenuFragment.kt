package com.mksk.server.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mksk.server.data.models.Product
import com.mksk.server.databinding.FragmentMenuBinding
import com.mksk.server.ui.menu.adapter.ProductAdapter
import com.mksk.server.utils.Constants.ACTION_INSERT
import com.mksk.server.utils.Constants.ACTION_UPDATE
import com.mksk.server.utils.SwipeToDeleteCallBack
import com.mksk.server.utils.callback.ItemClick
import com.mksk.server.utils.dialog.DialogProduct

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MenuViewModel by viewModels()
    private val dialogAdd = DialogProduct(ACTION_INSERT) { product ->
        viewModel.insertProduct(product)
    }

    private val dialogUpdate = DialogProduct(ACTION_UPDATE) { product ->
        viewModel.updateProduct(product)
    }

    private val itemClick = object : ItemClick<Product> {
        override fun onItemClick(t: Product) {
            dialogUpdate.showDialog(childFragmentManager,t)
        }
    }

    private val productAdapter = ProductAdapter(itemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.products.observe(this) {
            productAdapter.setData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnAdd.setOnClickListener {
                dialogAdd.showDialog(childFragmentManager)
            }

            rvProduct.apply {
                adapter = productAdapter
                layoutManager = LinearLayoutManager(requireActivity())
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
            }

            val swipeHandle = object : SwipeToDeleteCallBack(requireContext()) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    viewModel.apply {
                        deleteProduct(products.value?.get(position)!!)
                    }
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeHandle)
            itemTouchHelper.attachToRecyclerView(rvProduct)
        }

    }

}