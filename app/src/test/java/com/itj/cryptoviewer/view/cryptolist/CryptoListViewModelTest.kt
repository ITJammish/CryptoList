package com.itj.cryptoviewer.view.cryptolist

import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.ViewModelTestBase
import com.itj.cryptoviewer.data.CryptoServiceGetCoinsResponse
import com.itj.cryptoviewer.data.GetCoinsCoin
import com.itj.cryptoviewer.data.GetCoinsData
import com.itj.cryptoviewer.domain.FetchCryptoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Test

// TODO come back to this and investigate why LiveData.value is null
@ExperimentalCoroutinesApi
class CryptoListViewModelTest : ViewModelTestBase() {

    private companion object {
        const val COIN_NAME_BITCOIN = "BITCOIN"
    }

    private val coinsResponse = mockk<CryptoServiceGetCoinsResponse>().also {
        every { it.data } returns mockk<GetCoinsData>().also {
            every { it.coins } returns coinsResponseList
        }
    }
    private val coinsResponseList: List<GetCoinsCoin> = listOf(
        mockk<GetCoinsCoin>().also { every { it.name } returns COIN_NAME_BITCOIN }
    )

    private val mockTestDataObserver = mockk<Observer<List<GetCoinsCoin>>>().also {
        every { it.onChanged(any()) } returns Unit
    }
    private val mockFetchCryptoList = mockk<FetchCryptoList>().also {
        coEvery { it.invoke() } returns coinsResponse
    }

    private lateinit var subject: CryptoListViewModel

    @After
    fun tearDown() {
        subject.testData.removeObserver(mockTestDataObserver)
    }

    @Test
    fun getTestData() {
//        instantiateSubject()
    }

    @Test
    fun fetchData() {
        instantiateSubject()

        subject.fetchData()

        coVerify {
            mockFetchCryptoList.invoke()
        }

        coroutineTestRule.runCurrent()
        assertThat(subject.testData.value).isEqualTo(coinsResponseList)
    }

    private fun instantiateSubject(advanceUntilIdle: Boolean = true) {
        subject = CryptoListViewModel(mockFetchCryptoList)
//        subject.testData.observeForever(mockTestDataObserver)
        if (advanceUntilIdle) {
            coroutineTestRule.runCurrent()
        }
    }
}