package com.itj.cryptoviewer.data

import com.itj.cryptoviewer.data.utils.Resource
import com.itj.cryptoviewer.data.utils.Resource.Error
import com.itj.cryptoviewer.data.utils.Resource.Success
import com.itj.cryptoviewer.data.utils.ResourceErrorType
import com.itj.cryptoviewer.domain.model.Coin
import javax.inject.Inject

// should store result data in database
// another usecase/method should expose database data and allow views to observe/attach
class FetchCryptoRepositoryImpl @Inject constructor(
    private val service: CryptoService,
    private val mapper: NetworkCoinToDomainCoinMapper,
) : FetchCryptoRepository {

    override suspend fun requestCryptoInformation(): Resource<List<Coin>> {
        val response = service.getCoins()
        response?.let {
            if (it.isSuccessful) {
                // todo update the database with the result
                it.body()?.let { cryptoResponse ->
                    return cryptoResponse.data?.coins
                        ?.map { networkCoin -> mapper.mapNetworkCoinToDomainCoin(networkCoin) }
                        ?.let { coinList -> Success(coinList) }
                        ?: Error(ResourceErrorType.Generic)
                } ?: return Error(ResourceErrorType.Generic)
            } else {
                return Error(ResourceErrorType.Connection)
            }
        } ?: return Error(ResourceErrorType.Generic)
    }
}
