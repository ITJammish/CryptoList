package com.itj.cryptoviewer.view.cryptolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itj.cryptoviewer.R
import com.itj.cryptoviewer.data.GetCoinsCoin
import kotlinx.android.synthetic.main.view_coin_summary.view.*

class CryptoSummaryRecyclerViewAdapter : RecyclerView.Adapter<CryptoSummaryViewHolder>() {

    private var data: List<GetCoinsCoin> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoSummaryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_coin_summary, parent, false)
        return CryptoSummaryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CryptoSummaryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(coins: List<GetCoinsCoin>) {
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

    fun bind(coin: GetCoinsCoin) {
        itemView.coin_name_view.text = coin.name
    }
}
