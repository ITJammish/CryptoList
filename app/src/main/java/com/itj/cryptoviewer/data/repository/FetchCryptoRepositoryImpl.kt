package com.itj.cryptoviewer.data.repository

import com.itj.cryptoviewer.data.CryptoService
import com.itj.cryptoviewer.data.database.CoinDao
import com.itj.cryptoviewer.data.database.StoredCoin
import com.itj.cryptoviewer.data.mapper.NetworkCoinToDomainCoinMapper
import com.itj.cryptoviewer.data.mapper.NetworkCoinToStoredCoinMapper
import com.itj.cryptoviewer.data.utils.Resource
import com.itj.cryptoviewer.data.utils.Resource.Error
import com.itj.cryptoviewer.data.utils.Resource.Success
import com.itj.cryptoviewer.data.utils.ResourceErrorType.Connection
import com.itj.cryptoviewer.data.utils.ResourceErrorType.Generic
import com.itj.cryptoviewer.data.utils.isConnectionException
import com.itj.cryptoviewer.domain.model.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCryptoRepositoryImpl @Inject constructor(
    private val service: CryptoService,
    private val mapperNetToDomain: NetworkCoinToDomainCoinMapper,
    private val mapperNetToStorage: NetworkCoinToStoredCoinMapper,
    private val coinDao: CoinDao,
) : FetchCryptoRepository {

    override val coins: Flow<List<StoredCoin>> = coinDao.getRankedCoins()

    override suspend fun requestCryptoInformation(): Resource<List<Coin>> {
        try {
            val response = service.getCoins()
            response.let {
                if (it.isSuccessful) {
                    it.body()?.let { cryptoResponse ->
                        return cryptoResponse.data?.coins
                            ?.map { networkCoin ->
                                val coin = mapperNetToDomain.mapNetworkCoinToDomainCoin(networkCoin)
                                val storedCoin = mapperNetToStorage.mapNetworkCoinToStoredCoin(networkCoin)
                                coinDao.insert(storedCoin)
                                coin
                            }
                            ?.let { coinList -> Success(coinList) }
                            ?: Error(Generic)
                    } ?: return Error(Generic)
                } else {
                    return Error(Connection)
                }
            }
        } catch (e: Exception) {
            if (e.isConnectionException()) {
                return Error(Connection)
            }
            return Error(Generic)
        }
    }
}
