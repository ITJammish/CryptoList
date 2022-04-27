package com.itj.cryptoviewer.di

import android.app.Application
import android.content.Context
import com.itj.cryptoviewer.data.database.CoinDao
import com.itj.cryptoviewer.data.database.CoinRoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    companion object {

        @Provides
        @Singleton
        fun provideCoinRoomDatabase(context: Context): CoinRoomDatabase =
            lazy { CoinRoomDatabase.getDatabase(context) }.value

        @Provides
        fun provideCoinDao(database: CoinRoomDatabase): CoinDao = lazy { database.coinDao() }.value
    }
}
