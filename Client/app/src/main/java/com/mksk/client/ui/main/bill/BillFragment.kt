package com.mksk.client.ui.main.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mksk.client.data.models.RevenueWithRevenueDetail
import com.mksk.client.databinding.FragmentBillBinding
import com.mksk.client.ui.main.MainViewModel
import com.mksk.client.ui.main.bill.adapter.BillAdapter
import com.mksk.client.ui.main.order.OrderViewModel
import com.mksk.client.utils.Constants.format
import com.mksk.server.data.models.RevenueDetail
import java.text.NumberFormat
import java.util.*

class BillFragment : Fragment() {
    private lateinit var binding: FragmentBillBinding
    private val args: BillFragmentArgs by navArgs()
    private lateinit var revenueWithRevenueDetail: RevenueWithRevenueDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        revenueWithRevenueDetail = args.revenueWithRevenueDetail
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBillBinding.inflate(inflater, container, false)
        val billAdapter = BillAdapter()
        binding.apply {
            revenueWithRevenueDetail.apply {
                rvBill.apply {
                    adapter = billAdapter
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    billAdapter.submitData(args.productOrder.toList())
                }
                val vat = (revenue.totalMoney.toInt() * 10) / 100
                tvTotal.text = format.format(revenue.totalMoney.toInt() + vat).toString()
                tvVat.text = format.format(vat).toString()
                btnGoHome.setOnClickListener {
                    val action = BillFragmentDirections.actionBillFragmentToTableFragment()
                    findNavController().navigate(action)
                }
            }
        }
        return binding.root
    }

}