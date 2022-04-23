package com.itj.cryptoviewer.di

import android.app.Application
import android.content.Context
import com.itj.cryptoviewer.data.CryptoService
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context
}
