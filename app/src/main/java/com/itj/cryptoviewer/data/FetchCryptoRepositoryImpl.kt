package com.itj.cryptoviewer.data

import javax.inject.Inject

// todo ut
// todo run through and debug: https://developer.android.com/codelabs/basic-android-kotlin-training-getting-data-internet#5
class FetchCryptoRepositoryImpl @Inject constructor(
    private val service: CryptoService
) : FetchCryptoRepository {

    override suspend fun requestCryptoInformation() {
        val hiff = "hewllooo"
        val response = service.getCoins()
        val hi = "hewllooo"
    }
}
