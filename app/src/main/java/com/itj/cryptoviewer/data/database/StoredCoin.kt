package com.itj.cryptoviewer.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_bank")
data class StoredCoin(
    @PrimaryKey @ColumnInfo(name = "coinId") val uuid: String,
    val symbol: String,
    val name: String,
    val color: String,
    val iconUrl: String,
    val price: String,
    val change: String,
    val rank: Int,
    val sparkline: List<String>,
    val coinrankingUrl: String,
)
