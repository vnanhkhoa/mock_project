package com.mksk.server.ui.table

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mksk.server.R
import com.mksk.server.data.models.Table
import com.mksk.server.databinding.FragmentTableBinding
import com.mksk.server.ui.table.adapter.TableAdapter
import com.mksk.server.utils.SwipeToDeleteCallBack
import com.mksk.server.utils.callback.ItemClick


class TableFragment : Fragment() {
    private lateinit var binding: FragmentTableBinding
    private val viewModel: TableViewModel by viewModels()
    private val itemClick = object : ItemClick<Table> {
        override fun onItemClick(t: Table) {
            showDialog(t)
        }
    }

    private val adapter = TableAdapter(itemClick)
    private var nameTable = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.table.observe(requireActivity()) {
            if (!it.isNullOrEmpty()) {
                nameTable = it.size
            }
            adapter.submitData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTableBinding.inflate(inflater, container, false)

        binding.apply {
            rvTable.adapter = adapter
            rvTable.layoutManager =
                GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            btnAdd.setOnClickListener {
                val newTable = Table(name = nameTable + 1)
                viewModel.insertTable(newTable)
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
                    deleteTable(table.value!![position])
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandle)
        itemTouchHelper.attachToRecyclerView(binding.rvTable)
        return binding.root
    }

    fun showDialog(t: Table) {
        val inputEditTextField = EditText(requireActivity())
        inputEditTextField.setText("${t.name}")

        val container = FrameLayout(requireActivity())
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.leftMargin = resources.getDimensionPixelSize(R.dimen.dp_20)
        params.rightMargin = resources.getDimensionPixelSize(R.dimen.dp_20)
        inputEditTextField.layoutParams = params
        container.addView(inputEditTextField)

        val dialog =
            MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_rounded)
                .setTitle("Cập nhật tên bàn")
                .setView(container)
                .setPositiveButton("Cập nhật") { _, _ ->
                    val editTextInput = inputEditTextField.text.toString().trim()
                    val table = t.copy(
                        name = editTextInput.toInt()
                    )
                    viewModel.updateTable(table)
                }
                .setNegativeButton("Huỷ", null)
                .create()
        dialog.show()

        val btnPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        val btnNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        with(btnPositive) {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.orangeApp))
        }
        with(btnNegative) {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.red_700))
        }
    }
}