package com.itj.cryptoviewer.view.cryptolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itj.cryptoviewer.R
import com.itj.cryptoviewer.domain.model.Coin
import com.itj.cryptoviewer.domain.usecase.ConnectViewToDataSource
import com.itj.cryptoviewer.domain.usecase.FetchCryptoList
import com.itj.cryptoviewer.domain.utils.UseCaseResult.GenericErrorResult
import com.itj.cryptoviewer.domain.utils.UseCaseResult.NetworkErrorResult
import com.itj.cryptoviewer.domain.utils.UseCaseResult.SuccessResult
import com.itj.cryptoviewer.view.cryptolist.CryptoListViewModel.ErrorMessage.Empty
import com.itj.cryptoviewer.view.cryptolist.CryptoListViewModel.ErrorMessage.GenericError
import com.itj.cryptoviewer.view.cryptolist.CryptoListViewModel.ErrorMessage.NetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoListViewModel @Inject constructor(
    private val fetchCryptoList: FetchCryptoList,
    connectViewToDataSource: ConnectViewToDataSource,
) : ViewModel() {

    // TODO use state instead of flat data
    // Add loading state val
    val cryptoListData: Flow<List<Coin>> = connectViewToDataSource()
    val error: LiveData<ErrorMessage>
        get() = _error
    private val _error = MutableLiveData<ErrorMessage>()

    init {
        callCryptoListUseCase()
    }

    fun fetchData() {
        callCryptoListUseCase()
    }

    private fun callCryptoListUseCase() {
        viewModelScope.launch {
            _error.value = when (fetchCryptoList()) {
                is SuccessResult -> Empty
                is GenericErrorResult -> GenericError
                is NetworkErrorResult -> NetworkError
            }
        }
    }

    sealed class ErrorMessage(val resId: Int) {
        object Empty : ErrorMessage(-1)
        object GenericError : ErrorMessage(R.string.generic_error_message)
        object NetworkError : ErrorMessage(R.string.network_error_message)
    }
}
