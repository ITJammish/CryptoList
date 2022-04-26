package com.itj.cryptoviewer.domain

import com.itj.cryptoviewer.data.CryptoServiceGetCoinsResponse
import com.itj.cryptoviewer.data.FetchCryptoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchCryptoListTest {

    private val mockResponse = mockk<CryptoServiceGetCoinsResponse>()

    private val mockFetchCryptoRepository = mockk<FetchCryptoRepository>().also {
        coEvery { it.requestCryptoInformationTest() } returns mockResponse
    }

    private lateinit var subject: FetchCryptoList
    private lateinit var result: CryptoServiceGetCoinsResponse

    @Before
    fun setUp() {
        subject = FetchCryptoList(
            mockFetchCryptoRepository
        )
    }

    @Test
    operator fun invoke() {
        runBlocking {
            result = subject.invoke()
        }

        assertEquals(mockResponse, result)
    }
}
