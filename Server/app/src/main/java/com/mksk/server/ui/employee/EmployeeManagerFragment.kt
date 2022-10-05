package com.mksk.server.ui.employee

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mksk.server.R
import com.mksk.server.data.models.Employee
import com.mksk.server.databinding.FragmentEmployeeManagerBinding
import com.mksk.server.ui.employee.adapter.EmployeeAdapter
import com.mksk.server.utils.SwipeToDeleteCallBack
import com.mksk.server.utils.callback.ItemClick


class EmployeeManagerFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeManagerBinding
    private val viewModel: EmployeeViewModel by viewModels()
    private val adapter by lazy { EmployeeAdapter(itemClick) }
    private val itemClick = object : ItemClick<Employee> {
        override fun onItemClick(t: Employee) {
            val action =
                EmployeeManagerFragmentDirections.actionEmployeeManagerFragmentToEmployeeInfoFragment(
                    t
                )
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeManagerBinding.inflate(inflater, container, false)
        viewModel.getInfo.observe(viewLifecycleOwner) {
            adapter.submitData(it)
        }
        binding.apply {
            rvEmployees.adapter = adapter
            rvEmployees.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            fabAdd.setOnClickListener {
                val action =
                    EmployeeManagerFragmentDirections.actionEmployeeManagerFragmentToEmployeeAddFragment()
                findNavController().navigate(action)
            }
            textInputLayout.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    filter(s.toString())
                }
            })
            imgButtonSort.apply {
                var count = 0
                setOnClickListener {
                    viewModel.apply {
                        if (getInfo.value.isNullOrEmpty()) return@setOnClickListener
                        count++
                        when (count) {
                            1 -> {
                                setBackgroundResource(R.drawable.ic_up_arrow_check)
                                adapter.submitData(getInfo.value!!.sortedByDescending {
                                    it.name.substringAfterLast(" ")
                                })
                            }
                            2 -> {
                                setBackgroundResource(R.drawable.ic_down_arrow_check)
                                adapter.submitData(getInfo.value!!.sortedBy {
                                    it.name.substringAfterLast(" ")
                                })
                            }
                            else -> {
                                setBackgroundResource(R.drawable.ic_down_arrow)
                                adapter.submitData(getInfo.value!!)
                                count = 0
                            }
                        }
                    }

                }
            }
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
                    deleteEmployee(getInfo.value!![position])
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandle)
        itemTouchHelper.attachToRecyclerView(binding.rvEmployees)
        return binding.root
    }

    private fun filter(text: String) {
        viewModel.getInfo.value?.let {
            val result = it.filter { emp ->
                emp.name.lowercase().contains(text.lowercase())
            }
            adapter.submitData(result)
        }

    }
}