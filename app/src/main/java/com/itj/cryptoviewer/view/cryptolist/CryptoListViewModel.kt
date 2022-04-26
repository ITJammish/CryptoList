package com.itj.cryptoviewer.view.cryptolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itj.cryptoviewer.data.GetCoinsCoin
import com.itj.cryptoviewer.domain.FetchCryptoList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// TODO unit test - revisit
class CryptoListViewModel @Inject constructor(
    private var fetchCryptoList: FetchCryptoList
) : ViewModel() {

    val testData: LiveData<List<GetCoinsCoin>>
        get() = _testData
    private val _testData = MutableLiveData<List<GetCoinsCoin>>(emptyList())

    init {
        fetchData()
    }

    fun fetchData() {
        callCryptoListUseCase()
    }

    private fun callCryptoListUseCase() {
        viewModelScope.launch {
            val result = fetchCryptoList().data?.coins
            // todo pre-lunch bug: setting of null values (shouldn't be an issue post-layer mapping)
            //  reproduce by spamming 'refresh'
            _testData.value = result
        }
    }
}
