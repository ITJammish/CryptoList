package com.itj.cryptoviewer.domain.utils

sealed class UseCaseResult<out T> {
    data class SuccessResult<T>(val value: T) : UseCaseResult<T>()
    object NetworkErrorResult : UseCaseResult<Nothing>()
    object GenericErrorResult : UseCaseResult<Nothing>()
}
