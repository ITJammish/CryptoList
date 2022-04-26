package com.itj.cryptoviewer.view.cryptolist

import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.ViewModelTestBase
import com.itj.cryptoviewer.data.model.GetCoinsCoin
import com.itj.cryptoviewer.domain.FetchCryptoList
import com.itj.cryptoviewer.domain.model.Coin
import com.itj.cryptoviewer.domain.utils.UseCaseResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class CryptoListViewModelTest : ViewModelTestBase() {

    private companion object {
        const val COIN_NAME_BITCOIN = "BITCOIN"
    }

    private val coinsResponseList: List<Coin> = listOf(
        mockk<Coin>().also { every { it.name } returns COIN_NAME_BITCOIN }
    )
    private val coinsSuccessResponse = mockk<UseCaseResult.SuccessResult<List<Coin>>>()
    private val coinsGenericErrorResponse = mockk<UseCaseResult.GenericErrorResult>()
    private val coinsNetworkResponse = mockk<UseCaseResult.NetworkErrorResult>()

    private val mockFetchCryptoList = mockk<FetchCryptoList>()

    private lateinit var subject: CryptoListViewModel

    @Test
    fun getTestData_SuccessResult() {
        every { coinsSuccessResponse.value } returns coinsResponseList
        coEvery { mockFetchCryptoList.invoke() } returns coinsSuccessResponse

        instantiateSubject()

        coVerify {
            mockFetchCryptoList.invoke()
        }
        assertThat(subject.testData.value).isEqualTo(coinsResponseList)
    }

    @Test
    fun getTestData_GenericErrorResult() {
        coEvery { mockFetchCryptoList.invoke() } returns coinsGenericErrorResponse

        instantiateSubject()

        coVerify {
            mockFetchCryptoList.invoke()
        }
        assertThat(subject.testData.value).isEqualTo(emptyList<Coin>())
    }

    @Test
    fun getTestData_NetworkErrorResult() {
        coEvery { mockFetchCryptoList.invoke() } returns coinsNetworkResponse

        instantiateSubject()

        coVerify {
            mockFetchCryptoList.invoke()
        }
        assertThat(subject.testData.value).isEqualTo(emptyList<Coin>())
    }

    @Test
    fun fetchData() {
        every { coinsSuccessResponse.value } returnsMany listOf(emptyList(), coinsResponseList)
        coEvery { mockFetchCryptoList.invoke() } returns coinsSuccessResponse
        instantiateSubject()
        // Invoke on subject.init()
        coVerify {
            mockFetchCryptoList.invoke()
        }
        assertThat(subject.testData.value).isEqualTo(emptyList<GetCoinsCoin>())

        subject.fetchData()

        coroutineTestRule.runCurrent()
        coVerify {
            mockFetchCryptoList.invoke()
        }
        assertThat(subject.testData.value).isEqualTo(coinsResponseList)
    }

    private fun instantiateSubject(advanceUntilIdle: Boolean = true) {
        subject = CryptoListViewModel(mockFetchCryptoList)
        if (advanceUntilIdle) {
            coroutineTestRule.runCurrent()
        }
    }
}
