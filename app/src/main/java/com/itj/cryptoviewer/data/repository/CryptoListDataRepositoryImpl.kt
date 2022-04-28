package com.itj.cryptoviewer.data.repository

import com.itj.cryptoviewer.data.database.StoredCoin
import com.itj.cryptoviewer.data.mapper.StoredCoinToDomainCoinMapper
import com.itj.cryptoviewer.domain.model.Coin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CryptoListDataRepositoryImpl @Inject constructor(
    private val fetchCryptoRepository: FetchCryptoRepository,
    private val storedCoinToDomainCoinMapper: StoredCoinToDomainCoinMapper,
) : CryptoListDataRepository {

    override val cryptoListData: Flow<List<Coin>>
        get() = fetchCryptoRepository.coins.map { storedCoins -> mapStoredCoinsToCoins(storedCoins) }

    private fun mapStoredCoinsToCoins(storedCoins: List<StoredCoin>): List<Coin> {
        return storedCoins.map { storedCoin ->
            storedCoinToDomainCoinMapper.mapStoredCoinToDomainCoin(storedCoin)
        }
    }
}
