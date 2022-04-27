package com.itj.cryptoviewer.view.cryptolist.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.itj.cryptoviewer.R
import com.itj.cryptoviewer.domain.model.Coin
import com.itj.cryptoviewer.view.cryptolist.list.CryptoSummaryDiffUtilCallback.ChangePayload.UpdateChange
import com.itj.cryptoviewer.view.cryptolist.list.CryptoSummaryDiffUtilCallback.ChangePayload.UpdatePrice
import kotlinx.android.synthetic.main.item_view_coin_summary.view.*
import javax.inject.Inject

class CryptoSummaryRecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<CryptoSummaryViewHolder>() {

    private var data: List<Coin> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoSummaryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view_coin_summary, parent, false)
        return CryptoSummaryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CryptoSummaryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onBindViewHolder(holder: CryptoSummaryViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            (payloads[0] as ArrayList<*>).forEach { payload ->
                when (payload) {
                    is UpdateChange -> holder.bindMarketChange(data[position].change)
                    is UpdatePrice -> holder.bindMarketValue(data[position].price)
                }
            }
        } else {
            super.onBindViewHolder(holder, position, emptyList())
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(coins: List<Coin>) {
        val diffUtilCallback = CryptoSummaryDiffUtilCallback(oldList = data, newList = coins)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)

        data = coins
        diffResult.dispatchUpdatesTo(this)
    }
}

class CryptoSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(coin: Coin) {
        itemView.setBackgroundColor(Color.parseColor(coin.color.withTranslucency()))
        itemView.coin_name_view.text = coin.name
        fetchAndBindIcon(coin.iconUrl)
        itemView.coin_symbol_view.text = coin.symbol
        bindMarketValue(coin.price)
        bindMarketChange(coin.change)
    }

    fun bindMarketValue(coinMarketValue: String?) {
        itemView.coin_market_value.text = coinMarketValue?.let { "$$it" } ?: "NA"
    }

    fun bindMarketChange(coinChange: String?) {
        val formattedChange = coinChange ?: "NA"

        with(itemView.coin_change) {
            text = formattedChange
            coinChange?.let {
                val textColor = itemView.context.getColor(
                    if (it.isNegativeNumber()) {
                        R.color.red_strong
                    } else {
                        R.color.green_strong
                    }
                )
                setTextColor(textColor)
            }
        }
    }

    private fun fetchAndBindIcon(iconUrl: String?) {
        iconUrl?.let {
            val imageLoader = ImageLoader.Builder(itemView.context)
                .componentRegistry { add(SvgDecoder(itemView.context)) }
                .build()

            val request = ImageRequest.Builder(itemView.context)
                .crossfade(true)
                .crossfade(500)
                .placeholder(android.R.drawable.stat_notify_error)
                .error(android.R.drawable.stat_notify_error)
                .data(it)
                .target(itemView.coin_image_view)
                .build()

            imageLoader.enqueue(request)
        }
    }

    private fun String.isNegativeNumber(): Boolean {
        return this.contains('-')
    }

    private fun String.withTranslucency(): String {
        if (this.contains('#') && this.length == 7) {
            return "#66${this.split('#')[1]}"
        }
        return this
    }
}
