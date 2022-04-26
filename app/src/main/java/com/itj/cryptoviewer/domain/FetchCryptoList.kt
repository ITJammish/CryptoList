package com.itj.cryptoviewer.domain

import com.itj.cryptoviewer.data.CryptoServiceGetCoinsResponse
import com.itj.cryptoviewer.data.FetchCryptoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class FetchCryptoList @Inject constructor(
    private val repository: FetchCryptoRepository,
    @Named("ForFetchCryptoList") private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): CryptoServiceGetCoinsResponse {
        return withContext(dispatcher) {
//        repository.requestCryptoInformation()
            repository.requestCryptoInformationTest()
        }
    }
}
