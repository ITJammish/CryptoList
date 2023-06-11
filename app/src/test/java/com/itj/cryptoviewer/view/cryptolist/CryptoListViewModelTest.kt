package com.itj.cryptoviewer.view.cryptolist

import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.ViewModelTestBase
import com.itj.cryptoviewer.domain.model.Coin
import com.itj.cryptoviewer.domain.usecase.ConnectViewToDataSource
import com.itj.cryptoviewer.domain.usecase.FetchCryptoList
import com.itj.cryptoviewer.domain.utils.UseCaseResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CryptoListViewModelTest : ViewModelTestBase() {

    private val coinsResponseList: List<Coin> = listOf(
        mockk<Coin>().also { every { it.name } returns COIN_NAME_BITCOIN }
    )

    private val mockFetchCryptoList = mockk<FetchCryptoList>(relaxed = true)
    private val mockConnectViewToDataSource = mockk<ConnectViewToDataSource>().also {
        every { it.invoke() } returns MutableStateFlow(coinsResponseList)
    }

    private lateinit var subject: CryptoListViewModel

    @Before
    fun setUp() {
        subject = CryptoListViewModel(mockFetchCryptoList, mockConnectViewToDataSource)
    }

    @Test
    fun `WHEN initSubject THEN cryptoListData matches Database data`() = runTest {
        coEvery { mockFetchCryptoList.invoke() } returns UseCaseResult.SuccessResult(emptyList())

        subject.cryptoListData.test { results ->
            advanceUntilIdle()
            assertThat(results.size).isEqualTo(1)
            assertThat(results[0]).isEqualTo(coinsResponseList)
        }

        coVerify { mockConnectViewToDataSource.invoke() }
    }

    @Test
    fun `GIVEN SuccessResult WHEN fetchData THEN Empty error event`() = runTest {
        coEvery { mockFetchCryptoList.invoke() } returns UseCaseResult.SuccessResult(emptyList())

        subject.error.test { results ->
            subject.fetchData()
            advanceUntilIdle()
            assertThat(results.size).isEqualTo(1)
            assertThat(results[0]).isEqualTo(CryptoListViewModel.ErrorMessage.Empty)
        }
        coVerify {
            mockFetchCryptoList.invoke()
        }
    }

    @Test
    fun `GIVEN GenericErrorResult WHEN fetchData THEN GenericError event`() = runTest {
        coEvery { mockFetchCryptoList.invoke() } returns UseCaseResult.GenericErrorResult

        subject.error.test { results ->
            subject.fetchData()
            advanceUntilIdle()
            assertThat(results.size).isEqualTo(1)
            assertThat(results[0]).isEqualTo(CryptoListViewModel.ErrorMessage.GenericError)
        }
        coVerify {
            mockFetchCryptoList.invoke()
        }
    }

    @Test
    fun `GIVEN NetworkErrorResult WHEN fetchData THEN NetworkError event`() = runTest {
        coEvery { mockFetchCryptoList.invoke() } returns UseCaseResult.NetworkErrorResult

        subject.error.test { results ->
            subject.fetchData()
            advanceUntilIdle()
            assertThat(results.size).isEqualTo(1)
            assertThat(results[0]).isEqualTo(CryptoListViewModel.ErrorMessage.NetworkError)
        }
        coVerify {
            mockFetchCryptoList.invoke()
        }
    }

    private companion object {
        const val COIN_NAME_BITCOIN = "BITCOIN"
    }
}
