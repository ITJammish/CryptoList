package com.itj.cryptoviewer.data.mapper

import com.itj.cryptoviewer.data.database.StoredCoin
import com.itj.cryptoviewer.domain.model.Coin
import javax.inject.Inject

class StoredCoinToDomainCoinMapper @Inject constructor() {

    internal fun mapStoredCoinToDomainCoin(storedCoin: StoredCoin): Coin {
        return Coin(
            uuid = storedCoin.uuid,
            symbol = storedCoin.symbol,
            name = storedCoin.name,
            color = storedCoin.color,
            iconUrl = storedCoin.iconUrl,
            price = storedCoin.price,
            change = storedCoin.change,
            sparkline = storedCoin.sparkline,
            coinrankingUrl = storedCoin.coinrankingUrl,
        )
    }
}
