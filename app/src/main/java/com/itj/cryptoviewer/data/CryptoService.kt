package com.itj.cryptoviewer.data

import com.itj.cryptoviewer.data.model.CryptoServiceGetCoinsResponse
import retrofit2.Response
import retrofit2.http.GET

//https://api.coinranking.com/v2/coins?limit=5
interface CryptoService {
    @GET("v2/coins?limit=5")
    suspend fun getCoins(): Response<CryptoServiceGetCoinsResponse>?
}
