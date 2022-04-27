package com.itj.cryptoviewer.view.cryptolist.list

import androidx.recyclerview.widget.DiffUtil
import com.itj.cryptoviewer.domain.model.Coin
import com.itj.cryptoviewer.view.cryptolist.list.CryptoSummaryDiffUtilCallback.ChangePayload.UpdateChange
import com.itj.cryptoviewer.view.cryptolist.list.CryptoSummaryDiffUtilCallback.ChangePayload.UpdatePrice

class CryptoSummaryDiffUtilCallback constructor(
    private val oldList: List<Coin>,
    private val newList: List<Coin>,
) : DiffUtil.Callback() {

    sealed class ChangePayload {
        object UpdatePrice : ChangePayload()
        object UpdateChange : ChangePayload()
    }

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

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val payloads = mutableListOf<ChangePayload>()
        if (oldList[oldItemPosition].change != newList[newItemPosition].change) {
            payloads.add(UpdateChange)
        }
        if (oldList[oldItemPosition].price != newList[newItemPosition].price) {
            payloads.add(UpdatePrice)
        }
        if (payloads.isNotEmpty()) {
            return payloads
        }
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
