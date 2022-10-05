package com.mksk.client.ui.main.table

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mksk.client.databinding.FragmentTableBinding
import com.mksk.client.ui.main.MainViewModel
import com.mksk.server.utils.callback.ItemClick
import com.mksk.client.ui.main.table.adapter.TableAdapter
import com.mksk.server.data.models.Table

class TableFragment : Fragment() {
    private lateinit var binding: FragmentTableBinding
    private val viewModel: TableViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val itemClick = object : ItemClick<Table> {
        override fun onItemClick(t: Table) {
            val action = TableFragmentDirections.actionTableFragmentToOrderFragment(t)
            findNavController().navigate(action)
        }
    }
    private val tableAdapter = TableAdapter(itemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.tables.observe(requireActivity()) {
            tableAdapter.submitData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTableBinding.inflate(inflater, container, false)

        binding.recycleViewTable.apply {
            adapter = tableAdapter
            layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        }
        return binding.root
    }

}