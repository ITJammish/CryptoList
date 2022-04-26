package com.itj.cryptoviewer.data.utils

import com.itj.cryptoviewer.data.utils.Resource.Error
import com.itj.cryptoviewer.data.utils.Resource.Success

sealed class Resource<out T> {
    data class Success<T>(val value: T) : Resource<T>()
    data class Error(val type: ResourceErrorType) : Resource<Nothing>()
}

inline fun <R, T> Resource<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: ResourceErrorType) -> R
): R {
    return when (this) {
        is Success -> onSuccess(value)
        is Error -> onFailure(type)
    }
}