package com.itj.cryptoviewer.data.utils

import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class ResourceErrorType {
    object Connection : ResourceErrorType()
    data class HttpError(val errorCode: Int) : ResourceErrorType()
    object Generic : ResourceErrorType()

    val isUnauthorisedError: Boolean
        get() = (this as? HttpError)?.errorCode == HttpURLConnection.HTTP_UNAUTHORIZED

    val isInternalServerError: Boolean
        get() = (this as? HttpError)?.errorCode == HttpURLConnection.HTTP_INTERNAL_ERROR
}

fun Throwable.isConnectionException() =
    this is SocketException || this is SocketTimeoutException || this is UnknownHostException || this is IOException
