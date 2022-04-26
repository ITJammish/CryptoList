package com.itj.cryptoviewer.data.model

import com.google.gson.annotations.SerializedName

data class CryptoServiceGetCoinsResponse(
    val status: String?,
    val data: GetCoinsData?,
)

data class GetCoinsData(
    val stats: GetCoinsStats?,
    val coins: List<GetCoinsCoin>?,
)

data class GetCoinsStats(
    val total: Int?,
    val totalCoins: Int?,
    val totalMarkets: Int?,
    val totalExchanges: Int?,
    val totalMarketCap: String?,
    val total24hVolume: String?,
)

data class GetCoinsCoin(
    val uuid: String?,
    val symbol: String?,
    val name: String?,
    val color: String?,
    val iconUrl: String?,
    val marketCap: String?,
    val price: String?,
    val listedAt: Int?,
    val tier: Int?,
    val change: String?,
    val rank: Int?,
    val sparkline: List<String?>,
    val lowVolume: Boolean,
    val coinrankingUrl: String?,
    @SerializedName("24hVolume") val twentyFourHourVolume: String?,
    val btcPrice: String?,
)
