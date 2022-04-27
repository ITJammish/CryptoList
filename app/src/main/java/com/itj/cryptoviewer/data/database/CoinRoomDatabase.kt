package com.itj.cryptoviewer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [StoredCoin::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CoinRoomDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

    companion object {
        @Volatile
        private var INSTANCE: CoinRoomDatabase? = null

        fun getDatabase(context: Context): CoinRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinRoomDatabase::class.java,
                    "coin_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}