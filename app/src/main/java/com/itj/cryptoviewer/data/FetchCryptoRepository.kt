package com.itj.cryptoviewer.data

interface FetchCryptoRepository {

//    suspend fun requestCryptoInformation()

    suspend fun requestCryptoInformationTest(): CryptoServiceGetCoinsResponse
}
