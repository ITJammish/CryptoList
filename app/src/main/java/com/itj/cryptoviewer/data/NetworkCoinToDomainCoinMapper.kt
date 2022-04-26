package com.itj.cryptoviewer.data

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
            price = networkCoin.price ?: "",
            change = networkCoin.change ?: "",
            sparkline = mapSparkline(networkCoin.sparkline),
            coinrankingUrl = networkCoin.coinrankingUrl ?: "",
        )
    }

    private fun mapSparkline(networkSparklines: List<String?>): List<String> {
        // TODO consider how to display empty values on a graph
        return networkSparklines.map { sparkline -> sparkline ?: "" }
    }
}
