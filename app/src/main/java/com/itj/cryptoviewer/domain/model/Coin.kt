package com.itj.cryptoviewer.domain.model

data class Coin(
    val uuid: String,
    val symbol: String,
    val name: String,
    val color: String,
    val iconUrl: String,
    val price: String,
    val change: String,
    val sparkline: List<String>,
    val coinrankingUrl: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coin

        if (uuid != other.uuid) return false
        if (symbol != other.symbol) return false
        if (name != other.name) return false
        if (color != other.color) return false
        if (iconUrl != other.iconUrl) return false
        if (price != other.price) return false
        if (change != other.change) return false
        if (sparkline != other.sparkline) return false
        if (coinrankingUrl != other.coinrankingUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + symbol.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + iconUrl.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + change.hashCode()
        result = 31 * result + sparkline.hashCode()
        result = 31 * result + coinrankingUrl.hashCode()
        return result
    }
}
