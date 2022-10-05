package com.mksk.client.ui.main.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mksk.client.databinding.FragmentOrderBinding
import com.mksk.client.ui.main.MainViewModel
import com.mksk.client.ui.main.menu.MenuOrderFragment
import com.mksk.client.ui.main.menu.adapter.MenuOrderAdapter
import com.mksk.client.utils.callback.ItemClickOrderMenu
import com.mksk.client.utils.getDateCurrent
import com.mksk.client.utils.getTimeCurrent
import com.mksk.client.utils.merge
import com.mksk.server.data.models.Revenue
import com.mksk.server.data.models.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderFragment : Fragment() {
    private lateinit var binding: FragmentOrderBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: OrderViewModel by viewModels()
    private val args: OrderFragmentArgs by navArgs()
    private var isOpen = false
    private lateinit var table: Table
    private val itemClick = object : ItemClickOrderMenu {
        override fun addClick(idProduct: Int): Int {
            return viewModel.handleAdd(idProduct)
        }

        override fun subClick(idProduct: Int): Int {
            return viewModel.handleSud(idProduct)
        }
    }

    private val menuOrderAdapter by lazy { MenuOrderAdapter(itemClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        table = args.table
        viewModel.getOrderByTableId(table.id!!).observeForever {
            if (it != null) {
                viewModel.setRevenueWithRevenueDetail(it)
                viewModel.setListRD(it.listRevenueDetail)
            }
        }

        mainViewModel.products.observe(requireActivity()) {
            viewModel.setListProduct(it)
        }

        viewModel.productOrders.observe(requireActivity()) {
            menuOrderAdapter.submitData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        binding.apply {
            tvDayBook.text = getDateCurrent()
            tvTimeStart.text = getTimeCurrent()
            btnOrder.setOnClickListener {
                if (!isOpen) {
                    isOpen = true
                    val modalBottomSheet = MenuOrderFragment { order ->
                        lifecycleScope.launch(Dispatchers.IO) {
                            val total: Long = order.fold(0) { sum, pOrder ->
                                sum + pOrder.productAmount * pOrder.productPrice
                            }
                            val id = if (!table.status) {
                                table = table.copy(status = true)
                                viewModel.insertRevenue(
                                    Revenue(
                                        null,
                                        mainViewModel.employee.value!!.id!!,
                                        table.id!!,
                                        getDateCurrent(),
                                        total
                                    )
                                )
                            } else {
                                val revenue = viewModel.getRevenueWithRevenueDetail().revenue
                                revenue.date = getDateCurrent()
                                revenue.totalMoney + total

                                viewModel.apply {
                                    insertRevenue(revenue)
                                }
                                revenue.id!!
                            }

                            val revenueDetails = order.map { it.toRevenueDetail(id.toInt()) }
                            val insertList = merge(viewModel.getListRD(), revenueDetails)
                            val groupBy = insertList.groupBy({ it.idProduct }, { it })
                                .mapValues {
                                    it.value.reduce { left, right ->
                                        left.copy(amount = left.amount + right.amount)
                                    }
                                }.values
                            viewModel.insertRevenueDetail(*groupBy.toTypedArray())
                        }
                    }
                    modalBottomSheet.isCancelable = false
                    modalBottomSheet.setCancel { isOpen = false }
                    modalBottomSheet.show(childFragmentManager, "BottomSheet")
                }
            }
            btnPay.setOnClickListener {
                binding.apply {
                    viewModel.productOrders.value?.let {
                        val action = OrderFragmentDirections.actionOrderFragmentToPayFragment(
                            args.table,
                            it.values.toTypedArray(),
                            viewModel.getRevenueWithRevenueDetail()
                        )
                        findNavController().navigate(action)
                    }
                }
            }

            rvOrder.apply {
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
                adapter = menuOrderAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        mainViewModel.employee.observe(requireActivity()) {
            binding.apply {
                tvEmployeeName.text = it.name
            }
        }
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        if (table.status) {
            viewModel.apply {
                productOrders.value?.let {
                    if (it.isEmpty()) {
                        mainViewModel.deleteRevenue(getRevenueWithRevenueDetail().revenue)
                    }
                }
            }
        }
    }
}