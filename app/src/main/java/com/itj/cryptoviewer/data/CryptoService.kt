package com.itj.cryptoviewer.data

import retrofit2.Call
import retrofit2.http.GET

//https://api.coinranking.com/v2/coins?limit=5
interface CryptoService {
    @GET("v2/coins?limit=5")
    fun getCoins(): Call<CryptoServiceGetCoinsResponse>?
}
