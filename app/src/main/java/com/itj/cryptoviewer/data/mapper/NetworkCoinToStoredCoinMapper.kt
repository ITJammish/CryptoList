package com.itj.cryptoviewer.data.mapper

import android.util.Log
import com.itj.cryptoviewer.data.database.StoredCoin
import com.itj.cryptoviewer.data.model.GetCoinsCoin
import javax.inject.Inject

class NetworkCoinToStoredCoinMapper @Inject constructor() {

    internal fun mapNetworkCoinToStoredCoin(networkCoin: GetCoinsCoin): StoredCoin {
        return StoredCoin(
            uuid = networkCoin.uuid ?: "",
            symbol = networkCoin.symbol ?: "",
            name = networkCoin.name ?: "",
            color = networkCoin.color ?: "",
            iconUrl = networkCoin.iconUrl ?: "",
            price = networkCoin.price?.let { mapToTwoDecimalPlaces(it) } ?: "",
            change = networkCoin.change?.let { mapToTwoDecimalPlaces(it) } ?: "",
            rank = networkCoin.rank ?: -1,
            sparkline = mapSparkline(networkCoin.sparkline),
            coinrankingUrl = networkCoin.coinrankingUrl ?: "",
        )
    }

    private fun mapToTwoDecimalPlaces(networkPrice: String): String {
        val doubleValue: Double?
        try {
            doubleValue = networkPrice.toDouble()
        } catch (exception: NumberFormatException) {
            Log.e("CryptoSummaryRecyclerViewAdapter", exception.toString())
            return networkPrice
        }

        return String.format("%.2f", doubleValue)
    }

    private fun mapSparkline(networkSparklines: List<String?>): List<String> {
        // TODO consider how to display empty values on a graph
        return networkSparklines.map { sparkline -> sparkline ?: "" }
    }
}
