package com.itj.cryptoviewer.data.repository

import com.itj.cryptoviewer.data.database.StoredCoin
import com.itj.cryptoviewer.data.utils.Resource
import com.itj.cryptoviewer.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface FetchCryptoRepository {

    val coins: Flow<List<StoredCoin>>

    suspend fun requestCryptoInformation(): Resource<List<Coin>>
}
