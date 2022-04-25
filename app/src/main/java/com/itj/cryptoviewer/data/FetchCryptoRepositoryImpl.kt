package com.itj.cryptoviewer.data

import javax.inject.Inject

// todo ut
// todo run through and debug: https://developer.android.com/codelabs/basic-android-kotlin-training-getting-data-internet#5
class FetchCryptoRepositoryImpl @Inject constructor(
    private val service: CryptoService
) : FetchCryptoRepository {

    override suspend fun requestCryptoInformation() {
        val response = service.getCoins()
        response?.let {
            if (it.isSuccessful) {
                // update the database with the result
                val myResponse = it.body()
            } else {
                // return/notify of network issue
            }
        }
    }

    override suspend fun requestCryptoInformationTest(): CryptoServiceGetCoinsResponse {
        val response = service.getCoins()
        response?.let {
            if (it.isSuccessful) {
                // update the database with the result
                it.body()?.let { cryptoResponse ->
                    return cryptoResponse
                } ?: return CryptoServiceGetCoinsResponse("testFailed", null)
            } else {
                // return/notify of network issue
                return CryptoServiceGetCoinsResponse("testFailed", null)
            }
        } ?: return CryptoServiceGetCoinsResponse("testFailed", null)
    }
}
