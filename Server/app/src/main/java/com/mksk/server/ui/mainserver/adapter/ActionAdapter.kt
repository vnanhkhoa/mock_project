package com.mksk.server.ui.mainserver.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mksk.server.data.models.Action
import com.mksk.server.databinding.ItemManagerBinding
import com.mksk.server.utils.BaseDiffUtilCallBack

class ActionAdapter() :
    RecyclerView.Adapter<ActionAdapter.ViewHolder>() {
    private val actions = arrayListOf<Action>()

    class ViewHolder(private var binding: ItemManagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(action: Action) {
            binding.apply {
                tvActionName.text = action.actionName.trim()
                imgAction.setImageResource(action.iconAction)
            }

            itemView.setOnClickListener { action.action.invoke() }
        }
    }


    fun setData(data: List<Action>) {
        val diff = DiffUtil.calculateDiff(BaseDiffUtilCallBack(actions, data) { io, ine ->
            actions[io].actionName == data[ine].actionName
        })
        actions.clear()
        actions.addAll(data)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemManagerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action = actions[position]
        holder.bind(action)
    }

    override fun getItemCount(): Int = actions.size
}