package com.itj.cryptoviewer.view.cryptolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itj.cryptoviewer.domain.FetchCryptoList
import com.itj.cryptoviewer.domain.model.Coin
import com.itj.cryptoviewer.domain.utils.UseCaseResult.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoListViewModel @Inject constructor(
    private var fetchCryptoList: FetchCryptoList
) : ViewModel() {

    val testData: LiveData<List<Coin>>
        get() = _testData
    private val _testData = MutableLiveData<List<Coin>>(emptyList())

    init {
        callCryptoListUseCase()
    }

    fun fetchData() {
        callCryptoListUseCase()
    }

    // TODO consume and display generic/network error messages
    private fun callCryptoListUseCase() {
        viewModelScope.launch {
            _testData.value = when (val result = fetchCryptoList()) {
                is SuccessResult -> result.value
                is GenericErrorResult -> emptyList()
                is NetworkErrorResult -> emptyList()
            }
        }
    }
}
