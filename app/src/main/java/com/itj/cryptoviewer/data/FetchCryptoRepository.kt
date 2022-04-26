package com.itj.cryptoviewer.data

import com.itj.cryptoviewer.data.utils.Resource
import com.itj.cryptoviewer.domain.model.Coin

interface FetchCryptoRepository {

    suspend fun requestCryptoInformation(): Resource<List<Coin>>
}
