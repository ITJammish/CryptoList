package com.itj.cryptoviewer.view.cryptolist

import androidx.lifecycle.*
import com.itj.cryptoviewer.R
import com.itj.cryptoviewer.domain.usecase.ConnectViewToDataSource
import com.itj.cryptoviewer.domain.usecase.FetchCryptoList
import com.itj.cryptoviewer.domain.utils.UseCaseResult.*
import com.itj.cryptoviewer.view.cryptolist.CryptoListViewModel.ErrorMessage.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoListViewModel @Inject constructor(
    private val fetchCryptoList: FetchCryptoList,
    connectViewToDataSource: ConnectViewToDataSource,
) : ViewModel() {

    val cryptoListData = connectViewToDataSource().asLiveData()
    val error: LiveData<ErrorMessage>
        get() = _error
    private val _error = MutableLiveData<ErrorMessage>()

    init {
        callCryptoListUseCase()
    }

    fun fetchData() {
        callCryptoListUseCase()
    }

    // TODO consume and display generic/network error messages
    private fun callCryptoListUseCase() {
        viewModelScope.launch {
//            fetchCryptoList()
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
