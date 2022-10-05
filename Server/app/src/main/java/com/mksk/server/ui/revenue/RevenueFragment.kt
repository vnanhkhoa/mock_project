package com.mksk.server.ui.revenue

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mksk.server.R
import com.mksk.server.databinding.FragmentRevenueBinding
import com.mksk.server.ui.revenue.adapter.RevenueAdapter
import com.mksk.server.utils.callback.ItemClick
import java.text.SimpleDateFormat
import java.time.LocalDate


class RevenueFragment : Fragment() {
    private lateinit var binding: FragmentRevenueBinding
    private val viewModel: RevenueViewModel by viewModels()
    private val itemClick = object : ItemClick<String> {
        override fun onItemClick(t: String) {
            val action = RevenueFragmentDirections.actionRevenueFragmentToRevenueDetailFragment(t)
            findNavController().navigate(action)
        }
    }
    private val adapterRevenue = RevenueAdapter(itemClick)
    private var checkFilterTotal = false

    @SuppressLint("SimpleDateFormat")
    private val simpleFormat = SimpleDateFormat("dd-MM-yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.revenueForDates.observeForever {
            adapterRevenue.submitData(it)
        }
    }

    private val itemSelection = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            checkFilterTotal = when (position) {
                1 -> true
                else -> false
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRevenueBinding.inflate(inflater, container, false)
        val filterList = resources.getStringArray(R.array.filter)
        val adapterFilter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterList)
        binding.apply {
            recycleViewRevenue.apply {
                adapter = adapterRevenue
                layoutManager = LinearLayoutManager(context)
            }
            textInputLayout.editText?.addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) = filter(s.toString())
            })
            spnFilter.apply {
                adapter = adapterFilter
                onItemSelectedListener = itemSelection
            }

            imgButtonSort.apply {
                setOnClickListener {
                    handleSort()
                }
            }
        }

//        viewModel.products.observe(requireActivity()) {
//            viewModel.setData(it)
//        }
        return binding.root
    }

    private fun handleSort() {
        viewModel.apply {
            binding.imgButtonSort.apply {
                if (revenueForDates.value.isNullOrEmpty()) return
                count++
                when (count) {
                    1 -> {
                        setBackgroundResource(R.drawable.ic_up_arrow_check)
                        if (!checkFilterTotal) {
                            adapterRevenue.submitData(revenueForDates.value!!.sortedByDescending {
                                simpleFormat.parse(it.date.replace("/", "-"))?.time
                            })
                        } else {
                            adapterRevenue.submitData(revenueForDates.value!!.sortedByDescending {
                                it.total
                            })
                        }
                    }
                    2 -> {
                        setBackgroundResource(R.drawable.ic_down_arrow_check)
                        if (!checkFilterTotal) {
                            adapterRevenue.submitData(revenueForDates.value!!.sortedBy {
                                simpleFormat.parse(it.date.replace("/", "-"))?.time
                            })
                        } else {
                            adapterRevenue.submitData(revenueForDates.value!!.sortedBy {
                                it.total
                            })
                        }
                    }
                    else -> {
                        setBackgroundResource(R.drawable.ic_down_arrow)
                        count = 0
                    }
                }
                binding.recycleViewRevenue.scrollToPosition(0)
            }
        }
    }

    private fun filter(text: String) {
        viewModel.revenueForDates.value?.let {
            val result = it.filter { emp ->
                emp.date.lowercase().contains(text.lowercase())
            }
            adapterRevenue.submitData(result)
        }
    }
}