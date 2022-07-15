package com.learnbae.my.presentation.common

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallBack<T>(
    private val oldList: List<T>,
    private val updatedList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = updatedList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == updatedList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == updatedList[newItemPosition]
    }
}