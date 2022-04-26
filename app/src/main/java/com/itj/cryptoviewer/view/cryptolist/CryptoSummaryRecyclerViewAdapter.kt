package com.itj.cryptoviewer.view.cryptolist

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.itj.cryptoviewer.R
import com.itj.cryptoviewer.domain.model.Coin
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

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(coins: List<Coin>) {
        // set to local field
        data = coins
        // prompt adapter refresh
        notifyDataSetChanged()

        // TODO
        //  can also create a diff util -> have binding methods in ViewHolder per view so we can
        //  efficiently update only the views with changed data.
    }
}

class CryptoSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // TODO update with Domain model
    fun bind(coin: Coin) {
        itemView.setBackgroundColor(Color.parseColor(coin.color.withTranslucency()))
        itemView.coin_name_view.text = coin.name
        fetchAndBindIcon(coin.iconUrl)
        itemView.coin_symbol_view.text = coin.symbol
        bindMarketValue(coin.price)
        bindMarketChange(coin.change)
    }

    fun bindMarketValue(coinMarketValue: String?) {
        itemView.coin_market_value.text = coinMarketValue?.let {
            "$${it.withTwoDecimalPlaces()}"
        } ?: "NA"
    }

    fun bindMarketChange(coinChange: String?) {
        // todo make pill background to make coloured text stand out, that flips with theming
        val formattedChange = coinChange?.withTwoDecimalPlaces() ?: "NA"

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

    private fun String.withTwoDecimalPlaces(): String {
        val doubleValue: Double?
        try {
            doubleValue = this.toDouble()
        } catch (exception: NumberFormatException) {
            Log.e("CryptoSummaryRecyclerViewAdapter", exception.toString())
            return this
        }

        return String.format("%.2f", doubleValue)
    }

    private fun String.withTranslucency(): String {
        if (this.contains('#') && this.length == 7) {
            return "#66${this.split('#')[1]}"
        }
        return this
    }
}
