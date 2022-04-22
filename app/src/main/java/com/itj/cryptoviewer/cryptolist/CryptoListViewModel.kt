package com.itj.cryptoviewer.cryptolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CryptoListViewModel : ViewModel() {

    val message: LiveData<String>
        get() = _message

    private val _message: MutableLiveData<String> = MutableLiveData<String>("Hi")

    fun updateMessage() {
        _message.value = "bye"
    }
}
