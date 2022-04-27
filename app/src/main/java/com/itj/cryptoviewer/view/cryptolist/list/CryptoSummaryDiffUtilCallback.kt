package com.itj.cryptoviewer.view.cryptolist.list

import androidx.recyclerview.widget.DiffUtil
import com.itj.cryptoviewer.domain.model.Coin

class CryptoSummaryDiffUtilCallback constructor(
    private val oldList: List<Coin>,
    private val newList: List<Coin>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].uuid == newList[newItemPosition].uuid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
