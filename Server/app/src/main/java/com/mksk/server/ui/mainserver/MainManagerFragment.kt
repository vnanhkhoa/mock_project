package com.mksk.server.ui.mainserver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.mksk.server.R
import com.mksk.server.data.models.Action
import com.mksk.server.databinding.FragmentMainManagerBinding
import com.mksk.server.ui.mainserver.adapter.ActionAdapter


class MainManagerFragment : Fragment() {
    private lateinit var binding: FragmentMainManagerBinding
    private val actionAdapter = ActionAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainManagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycleViewManager.apply {
            adapter = actionAdapter
            layoutManager = GridLayoutManager(context, 2, VERTICAL, false)
        }
        actionAdapter.setData(getActionList())
    }

    private fun getActionList(): List<Action> {
        val actionNames = resources.getStringArray(R.array.action)
        val actions = arrayListOf<Action>()
        val actionIcons = createListIcon()
        val actionClick = createAction()

        for (i in 0..4) actions.add(Action(actionIcons[i], actionNames[i], actionClick[i]))
        return actions
    }

    private fun createAction(): List<() -> Unit> {
        return listOf(
            {
                findNavController().navigate(R.id.action_mainManagerFragment_to_employeeManagerFragment)
            },
            {
                findNavController().navigate(R.id.action_mainManagerFragment_to_revenueFragment)
            },
            {
                findNavController().navigate(R.id.action_mainManagerFragment_to_tableFragment)
            },
            {
                findNavController().navigate(R.id.action_mainManagerFragment_to_menuFragment)
            },
            {
                findNavController().navigate(R.id.action_mainManagerFragment_to_loginFragment)
            }
        )
    }

    private fun createListIcon(): List<Int> {
        return listOf(
            R.drawable.ic_baseline_group_24,
            R.drawable.ic_bill_24,
            R.drawable.ic_table_com,
            R.drawable.ic_baseline_menu_24,
            R.drawable.ic_baseline_logout_24
        )
    }
}