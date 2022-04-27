package com.itj.cryptoviewer.data

import android.util.Log
import com.itj.cryptoviewer.data.model.GetCoinsCoin
import com.itj.cryptoviewer.domain.model.Coin
import javax.inject.Inject

class NetworkCoinToDomainCoinMapper @Inject constructor() {

    internal fun mapNetworkCoinToDomainCoin(networkCoin: GetCoinsCoin): Coin {
        return Coin(
            uuid = networkCoin.uuid ?: "",
            symbol = networkCoin.symbol ?: "",
            name = networkCoin.name ?: "",
            color = networkCoin.color ?: "",
            iconUrl = networkCoin.iconUrl ?: "",
            price = networkCoin.price?.let { mapToTwoDecimalPlaces(it) } ?: "",
            change = networkCoin.change?.let { mapToTwoDecimalPlaces(it) } ?: "",
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
