package com.mksk.client.ui.main.pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mksk.client.data.models.RevenueWithRevenueDetail
import com.mksk.client.databinding.FragmentPayBinding
import com.mksk.client.ui.main.MainViewModel
import com.mksk.client.ui.main.pay.adapter.PayAdapter
import com.mksk.client.utils.Constants.format
import com.mksk.client.utils.getDateCurrent
import com.mksk.client.utils.getTimeCurrent

class PayFragment : Fragment() {
    private lateinit var binding: FragmentPayBinding
    private val args: PayFragmentArgs by navArgs()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: PayViewModel by viewModels()
    private lateinit var revenueWithRevenueDetail: RevenueWithRevenueDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        revenueWithRevenueDetail = args.revenueWithRevenueDetail
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayBinding.inflate(inflater, container, false)
        val payAdapter = PayAdapter()

        binding.apply {
            tvEmployeeName.text = mainViewModel.employee.value?.name
            tvTableNumber.text = args.table.name.toString()
            tvTimeStart.text = getTimeCurrent()
            tvDayBook.text = getDateCurrent()
            rvProductOrder.apply {
                adapter = payAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                payAdapter.submitData(args.productOrder.toList())
            }

            tvTotalMoney.text =
                format.format(revenueWithRevenueDetail.revenue.totalMoney).toString()
            btnPay.setOnClickListener {
                revenueWithRevenueDetail.apply {
                    val action = PayFragmentDirections.actionPayFragmentToBillFragment(
                        this,
                        args.productOrder,
                        args.table.name.toString()
                    )
                    mainViewModel.apply {
                        service.value?.sendRevenue(
                            revenue,
                            listRevenueDetail
                        )

                        deleteRevenue(revenue)
                        viewModel.deleteByIdRevenue(revenue.id!!)
                    }
                    findNavController().navigate(action)
                }
            }
        }
        return binding.root
    }
}