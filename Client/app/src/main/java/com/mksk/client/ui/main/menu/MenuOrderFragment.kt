package com.mksk.client.ui.main.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mksk.client.data.models.ProductOrder
import com.mksk.client.databinding.FragmentMenuOrderBinding
import com.mksk.client.ui.main.MainViewModel
import com.mksk.client.ui.main.menu.adapter.MenuOrderAdapter
import com.mksk.client.utils.callback.ItemClickOrderMenu

class MenuOrderFragment(private val clickOrder: (order: List<ProductOrder>) -> Unit) :
    BottomSheetDialogFragment(),ItemClickOrderMenu {
    private lateinit var binding: FragmentMenuOrderBinding
    private val viewModel: MenuOrderViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val menuOrderAdapter by lazy { MenuOrderAdapter(this) }
    private var callBackCancel: (() -> Unit)? = null

    override fun addClick(idProduct: Int): Int {
        return viewModel.handleAdd(idProduct)
    }

    override fun subClick(idProduct: Int): Int {
        return viewModel.handleSud(idProduct)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("MenuOrderFragment", "onCreate: ")
        mainViewModel.products.observe(requireActivity()) {
            viewModel.getListProduct(it)
        }

        viewModel.productOrder.observe(requireActivity()) {
            menuOrderAdapter.submitData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.e("MenuOrderFragment", "onResume: ")
        Log.e("MenuOrderFragment", "${viewModel.productOrder.value}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("MenuOrderFragment", "onViewCreated: ")

        binding.apply {
            recycleViewMenu.apply {
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
                adapter = menuOrderAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            imgBtnClose.setOnClickListener {
                dismiss()
            }

            btnOrder.setOnClickListener {
                val listOrder = viewModel.productOrder.value!!
                val a = listOrder.filter { (_, value) ->
                    value.productAmount > 0
                }
                clickOrder(a.values.toList())
                dismiss()
            }
        }

        val behavior = BottomSheetBehavior.from(binding.root.parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
    }

    fun setCancel(callBack: () -> Unit) {
        callBackCancel = callBack
    }

    override fun onDetach() {
        super.onDetach()
        callBackCancel?.let { it() }
        callBackCancel = null
        Log.e("MenuOrderFragment", "onDetach: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("MenuOrderFragment", "onSaveInstanceState: ")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.e("MenuOrderFragment", "onViewStateRestored: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("MenuOrderFragment", "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.e("MenuOrderFragment", "onDestroy: ")
    }
}
