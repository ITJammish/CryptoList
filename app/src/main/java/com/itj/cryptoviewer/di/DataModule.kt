package com.itj.cryptoviewer.di

import com.itj.cryptoviewer.data.repository.CryptoListDataRepository
import com.itj.cryptoviewer.data.repository.CryptoListDataRepositoryImpl
import com.itj.cryptoviewer.data.repository.FetchCryptoRepository
import com.itj.cryptoviewer.data.repository.FetchCryptoRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun provideCryptoListDataRepository(
        cryptoListDataRepositoryImpl: CryptoListDataRepositoryImpl,
    ): CryptoListDataRepository
}
