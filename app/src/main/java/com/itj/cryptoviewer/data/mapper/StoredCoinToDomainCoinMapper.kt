package com.itj.cryptoviewer.data.mapper

import com.itj.cryptoviewer.data.database.StoredCoin
import com.itj.cryptoviewer.domain.model.Coin
import javax.inject.Inject

class StoredCoinToDomainCoinMapper @Inject constructor() {

    internal fun mapStoredCoinToDomainCoin(storedCoin: StoredCoin): Coin = with(storedCoin) {
        Coin(
            uuid = uuid,
            symbol = symbol,
            name = name,
            color = color,
            iconUrl = iconUrl,
            price = price,
            change = change,
            sparkline = sparkline,
            coinrankingUrl = coinrankingUrl,
        )
    }
}
