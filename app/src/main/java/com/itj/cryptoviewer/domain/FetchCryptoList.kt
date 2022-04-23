package com.itj.cryptoviewer.domain

import com.itj.cryptoviewer.data.FetchCryptoRepository
import javax.inject.Inject

// todo ut
class FetchCryptoList @Inject constructor(private val repository: FetchCryptoRepository) {

    suspend operator fun invoke() {
        repository.requestCryptoInformation()
    }
}
