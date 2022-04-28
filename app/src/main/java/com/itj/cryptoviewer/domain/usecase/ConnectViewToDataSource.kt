package com.itj.cryptoviewer.domain.usecase

import com.itj.cryptoviewer.data.repository.CryptoListDataRepository
import com.itj.cryptoviewer.domain.model.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConnectViewToDataSource @Inject constructor(
    private val repository: CryptoListDataRepository,
) {

    operator fun invoke(): Flow<List<Coin>> {
        return repository.cryptoListData
    }
}
