package com.mksk.server.ui.revenuedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mksk.server.data.models.RevenueDetail
import com.mksk.server.databinding.FragmentRevenueDetailBinding
import com.mksk.server.ui.revenuedetail.adapter.RevenueDetailAdapter

class RevenueDetailFragment : Fragment() {

    private lateinit var binding: FragmentRevenueDetailBinding
    private val viewModel: RevenueDetailViewModel by viewModels()
    private val navArgs: RevenueDetailFragmentArgs by navArgs()
    private val revenueDetailAdapter = RevenueDetailAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val date = navArgs.date

        viewModel.getRevenueForProducts(date).observeForever {
            revenueDetailAdapter.submitData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRevenueDetailBinding.inflate(inflater, container, false)

        binding.recycleViewRevenueDetail.apply {
            adapter = revenueDetailAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        return binding.root
    }


}