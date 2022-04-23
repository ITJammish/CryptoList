package com.itj.cryptoviewer.di

import com.itj.cryptoviewer.data.FetchCryptoRepository
import com.itj.cryptoviewer.data.FetchCryptoRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideFetchCryptoRepository(
        fetchCryptoRepositoryImpl: FetchCryptoRepositoryImpl,
    ): FetchCryptoRepository
}
