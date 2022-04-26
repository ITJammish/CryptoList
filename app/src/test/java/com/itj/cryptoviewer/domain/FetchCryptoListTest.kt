package com.itj.cryptoviewer.domain

import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.data.FetchCryptoRepository
import com.itj.cryptoviewer.data.utils.Resource
import com.itj.cryptoviewer.data.utils.ResourceErrorType
import com.itj.cryptoviewer.domain.model.Coin
import com.itj.cryptoviewer.domain.utils.UseCaseResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchCryptoListTest {

    private val mockCoin = mockk<Coin>()
    private val mockRepositorySuccessResult = mockk<Resource.Success<List<Coin>>>()
    private val mockRepositoryConnectionErrorResult = mockk<Resource.Error>().also {
        every { it.type } returns ResourceErrorType.Connection
    }
    private val mockRepositoryGenericErrorResult = mockk<Resource.Error>().also {
        every { it.type } returns ResourceErrorType.Generic
    }

    private val mockFetchCryptoRepository = mockk<FetchCryptoRepository>()

    private lateinit var subject: FetchCryptoList
    private lateinit var result: UseCaseResult<List<Coin>>

    @Before
    fun setUp() {
        subject = FetchCryptoList(
            mockFetchCryptoRepository
        )
    }

    @Test
    fun invokeWithSuccessResource() {
        every { mockRepositorySuccessResult.value } returns listOf(mockCoin)
        coEvery { mockFetchCryptoRepository.requestCryptoInformation() } returns mockRepositorySuccessResult

        runBlocking {
            result = subject.invoke()
        }

        coVerify { mockFetchCryptoRepository.requestCryptoInformation() }
        assertThat(result).isEqualTo(UseCaseResult.SuccessResult(listOf(mockCoin)))
    }

    @Test
    fun invokeWithConnectionErrorResource() {
        coEvery { mockFetchCryptoRepository.requestCryptoInformation() } returns mockRepositoryConnectionErrorResult

        runBlocking {
            result = subject.invoke()
        }

        coVerify { mockFetchCryptoRepository.requestCryptoInformation() }
        assertThat(result).isEqualTo(UseCaseResult.NetworkErrorResult)
    }

    @Test
    fun invokeWithGenericErrorResource() {
        coEvery { mockFetchCryptoRepository.requestCryptoInformation() } returns mockRepositoryGenericErrorResult

        runBlocking {
            result = subject.invoke()
        }

        coVerify { mockFetchCryptoRepository.requestCryptoInformation() }
        assertThat(result).isEqualTo(UseCaseResult.GenericErrorResult)
    }
}
