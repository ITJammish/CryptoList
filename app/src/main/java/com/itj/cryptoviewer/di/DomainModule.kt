package com.itj.cryptoviewer.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module(includes = [DataModule::class])
abstract class DomainModule {

    companion object {
        @Named("ForFetchCryptoList")
        @Provides
        fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}
