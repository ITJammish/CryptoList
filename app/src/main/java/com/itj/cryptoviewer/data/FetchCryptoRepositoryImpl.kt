package com.itj.cryptoviewer.data

import com.itj.cryptoviewer.data.database.CoinDao
import com.itj.cryptoviewer.data.database.StoredCoin
import com.itj.cryptoviewer.data.utils.Resource
import com.itj.cryptoviewer.data.utils.Resource.Error
import com.itj.cryptoviewer.data.utils.Resource.Success
import com.itj.cryptoviewer.data.utils.ResourceErrorType
import com.itj.cryptoviewer.data.utils.ResourceErrorType.Connection
import com.itj.cryptoviewer.data.utils.ResourceErrorType.Generic
import com.itj.cryptoviewer.domain.model.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * TODO THURSDAY
 *  Connect View to Flow! -> second UseCase
 *  Toast on error
 *
 *  Later...
 *  Second mapper UnitTest
 *  README
 *  Second screen?
 */
class FetchCryptoRepositoryImpl @Inject constructor(
    private val service: CryptoService,
    private val mapperNetToDomain: NetworkCoinToDomainCoinMapper,
    private val mapperNetToStorage: NetworkCoinToStoredCoinMapper,
    private val coinDao: CoinDao,
) : FetchCryptoRepository {

    val coins: Flow<List<StoredCoin>> = coinDao.getRankedCoins()

    override suspend fun requestCryptoInformation(): Resource<List<Coin>> {
        val response = service.getCoins()
        response?.let {
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
        } ?: return Error(Generic)
    }
}
