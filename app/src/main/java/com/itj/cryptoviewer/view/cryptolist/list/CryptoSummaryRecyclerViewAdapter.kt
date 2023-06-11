package com.itj.cryptoviewer.view.cryptolist.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.itj.cryptoviewer.R
import com.itj.cryptoviewer.databinding.ItemViewCoinSummaryBinding
import com.itj.cryptoviewer.domain.model.Coin
import com.itj.cryptoviewer.view.cryptolist.list.CryptoSummaryDiffUtilCallback.ChangePayload.UpdateChange
import com.itj.cryptoviewer.view.cryptolist.list.CryptoSummaryDiffUtilCallback.ChangePayload.UpdatePrice
import javax.inject.Inject

class CryptoSummaryRecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<CryptoSummaryViewHolder>() {

    private var data: List<Coin> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoSummaryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewCoinSummaryBinding.inflate(layoutInflater, parent, false)
        return CryptoSummaryViewHolder(binding)
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

class CryptoSummaryViewHolder(private val binding: ItemViewCoinSummaryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(coin: Coin) {
        itemView.setBackgroundColor(Color.parseColor(coin.color.withTranslucency()))
        binding.coinNameView.text = coin.name
        fetchAndBindIcon(coin.iconUrl)
        binding.coinSymbolView.text = coin.symbol
        bindMarketValue(coin.price)
        bindMarketChange(coin.change)
    }

    fun bindMarketValue(coinMarketValue: String?) {
        binding.coinMarketValue.text = coinMarketValue?.let { "$$it" } ?: "NA"
    }

    fun bindMarketChange(coinChange: String?) {
        val formattedChange = coinChange ?: "NA"

        with(binding.coinChange) {
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
                .components { add(SvgDecoder.Factory()) }
                .build()

            val request = ImageRequest.Builder(itemView.context)
                .crossfade(true)
                .crossfade(500)
                .placeholder(android.R.drawable.stat_notify_error)
                .error(android.R.drawable.stat_notify_error)
                .data(it)
                .target(binding.coinImageView)
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
