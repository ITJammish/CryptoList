package com.itj.cryptoviewer.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.itj.cryptoviewer.BuildConfig
import com.itj.cryptoviewer.data.CryptoService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val CRYPTO_API_BASE_URL = "https://api.coinranking.com/"
        private const val CRYPTO_API_KEY_KEY = "x-access-token"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val modifiedRequest = chain.request().newBuilder()
                .addHeader(CRYPTO_API_KEY_KEY, BuildConfig.API_KEY)
                .build()
            chain.proceed(modifiedRequest)
        }
        .build()

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(CRYPTO_API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CryptoService = retrofit.create(CryptoService::class.java)
}
