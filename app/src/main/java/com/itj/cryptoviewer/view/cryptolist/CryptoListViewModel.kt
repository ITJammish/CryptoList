package com.itj.cryptoviewer.view.cryptolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itj.cryptoviewer.domain.FetchCryptoList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoListViewModel @Inject constructor(
    private var fetchCryptoList: FetchCryptoList
) : ViewModel() {

    val message: LiveData<String>
        get() = _message

    private val _message: MutableLiveData<String> = MutableLiveData<String>("Hi")

    init {
        callCryptoListUseCase()
    }

    fun updateMessage() {
        _message.value = "bye"
    }

    private fun callCryptoListUseCase() {
        /**
         * TODO
         *  UseCase
         *  Repository -> data source (network + database
         *  Network/API call
         */

        viewModelScope.launch(Dispatchers.IO) {
            fetchCryptoList()
        }
    }
}
