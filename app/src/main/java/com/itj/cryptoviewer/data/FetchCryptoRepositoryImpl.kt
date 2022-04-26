package com.itj.cryptoviewer.data

import javax.inject.Inject

class FetchCryptoRepositoryImpl @Inject constructor(
    private val service: CryptoService
) : FetchCryptoRepository {

//    override suspend fun requestCryptoInformation() {
//        val response = service.getCoins()
//        response?.let {
//            if (it.isSuccessful) {
//                // update the database with the result
//                val myResponse = it.body()
//            } else {
//                // return/notify of network issue
//            }
//        }
//    }

    override suspend fun requestCryptoInformationTest(): CryptoServiceGetCoinsResponse {
        val response = service.getCoins()
        response?.let {
            if (it.isSuccessful) {
                // update the database with the result
                it.body()?.let { cryptoResponse ->
                    return cryptoResponse
                } ?: return CryptoServiceGetCoinsResponse("testFailed - body was null", null)
            } else {
                // return/notify of network issue
                return CryptoServiceGetCoinsResponse("testFailed - request unsuccessful", null)
            }
        } ?: return CryptoServiceGetCoinsResponse("testFailed - response was null", null)
    }
}
