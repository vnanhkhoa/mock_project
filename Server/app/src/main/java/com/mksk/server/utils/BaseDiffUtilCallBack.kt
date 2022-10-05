package com.mksk.server.utils

import androidx.recyclerview.widget.DiffUtil


class BaseDiffUtilCallBack<T>(
    private val old: List<T>,
    private val new: List<T>,
    private val logic: (oldItemPosition: Int, newItemPosition: Int) -> Boolean
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        logic(oldItemPosition, newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old === new
}
