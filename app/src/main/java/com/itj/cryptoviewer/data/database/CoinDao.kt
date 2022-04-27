package com.itj.cryptoviewer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin_bank ORDER BY rank DESC")
    fun getRankedCoins(): Flow<List<StoredCoin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: StoredCoin)

    @Query("DELETE FROM coin_bank")
    suspend fun deleteAll()
}
