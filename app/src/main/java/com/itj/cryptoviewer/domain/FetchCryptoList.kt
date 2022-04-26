package com.itj.cryptoviewer.domain

import com.itj.cryptoviewer.data.FetchCryptoRepository
import com.itj.cryptoviewer.data.utils.ResourceErrorType.Connection
import com.itj.cryptoviewer.data.utils.fold
import com.itj.cryptoviewer.domain.model.Coin
import com.itj.cryptoviewer.domain.utils.UseCaseResult
import com.itj.cryptoviewer.domain.utils.UseCaseResult.GenericErrorResult
import com.itj.cryptoviewer.domain.utils.UseCaseResult.NetworkErrorResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class FetchCryptoList @Inject constructor(
    private val repository: FetchCryptoRepository,
    @Named("ForFetchCryptoList") private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): UseCaseResult<List<Coin>> {
        return withContext(dispatcher) {
            repository.requestCryptoInformation().fold(
                onSuccess = { coins -> UseCaseResult.SuccessResult(coins) },
                onFailure = { errorType ->
                    when (errorType) {
                        is Connection -> NetworkErrorResult
                        else -> GenericErrorResult
                    }
                }
            )
        }
    }
}
