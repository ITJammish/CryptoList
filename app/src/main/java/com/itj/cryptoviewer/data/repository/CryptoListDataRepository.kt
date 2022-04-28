package com.itj.cryptoviewer.data.repository

import com.itj.cryptoviewer.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface CryptoListDataRepository {

    val cryptoListData: Flow<List<Coin>>
}
