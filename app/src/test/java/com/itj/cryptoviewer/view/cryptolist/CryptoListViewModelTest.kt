package com.itj.cryptoviewer.view.cryptolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.ViewModelTestBase
import com.itj.cryptoviewer.domain.model.Coin
import com.itj.cryptoviewer.domain.usecase.ConnectViewToDataSource
import com.itj.cryptoviewer.domain.usecase.FetchCryptoList
import com.itj.cryptoviewer.domain.utils.UseCaseResult
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import org.junit.Before
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
    private val mockConnectViewToDataSource = mockk<ConnectViewToDataSource>()

    private lateinit var subject: CryptoListViewModel

    @Before
    fun setUp() {
        mockkStatic("androidx.lifecycle.FlowLiveDataConversions")

        mockConnectViewToDataSource.also {
            every { it.invoke() } returns mockk<Flow<List<Coin>>>().also {
                every { it.asLiveData() } returns mockk<LiveData<List<Coin>>>().also {
                    every { it.value } returns coinsResponseList
                }
            }
        }
    }

    @Test
    fun initSubject_cryptoListDataMatchesDatabaseData() {
        instantiateSubject()

        coVerify {
            mockConnectViewToDataSource.invoke()
        }
        assertThat(subject.cryptoListData.value).isEqualTo(coinsResponseList)
    }

    @Test
    fun getTestData_SuccessResult() {
        coEvery { mockFetchCryptoList.invoke() } returns coinsSuccessResponse

        instantiateSubject()

        coVerify {
            mockFetchCryptoList.invoke()
        }
        assertThat(subject.error.value).isEqualTo(CryptoListViewModel.ErrorMessage.Empty)
    }

    @Test
    fun getTestData_GenericErrorResult() {
        coEvery { mockFetchCryptoList.invoke() } returns coinsGenericErrorResponse

        instantiateSubject()

        coVerify {
            mockFetchCryptoList.invoke()
        }
        assertThat(subject.error.value).isEqualTo(CryptoListViewModel.ErrorMessage.GenericError)
    }

    @Test
    fun getTestData_NetworkErrorResult() {
        coEvery { mockFetchCryptoList.invoke() } returns coinsNetworkResponse

        instantiateSubject()

        coVerify {
            mockFetchCryptoList.invoke()
        }
        assertThat(subject.error.value).isEqualTo(CryptoListViewModel.ErrorMessage.NetworkError)
    }

    @Test
    fun fetchData() {
        coEvery { mockFetchCryptoList.invoke() } returns coinsSuccessResponse
        instantiateSubject()
        // Invoke on subject.init()
        coVerify {
            mockFetchCryptoList.invoke()
        }
        assertThat(subject.error.value).isEqualTo(CryptoListViewModel.ErrorMessage.Empty)

        subject.fetchData()

        coroutineTestRule.runCurrent()
        coVerify {
            mockFetchCryptoList.invoke()
        }
        assertThat(subject.error.value).isEqualTo(CryptoListViewModel.ErrorMessage.Empty)
    }

    private fun instantiateSubject(advanceUntilIdle: Boolean = true) {
        subject = CryptoListViewModel(mockFetchCryptoList, mockConnectViewToDataSource)
        if (advanceUntilIdle) {
            coroutineTestRule.runCurrent()
        }
    }
}
