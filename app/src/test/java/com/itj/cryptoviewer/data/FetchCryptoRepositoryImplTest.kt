package com.itj.cryptoviewer.data

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class FetchCryptoRepositoryImplTest {

    private companion object {
        val mockCryptoServiceGetCoinsResponse = CryptoServiceGetCoinsResponse(null, null)
    }

    private val mockService = mockk<CryptoService>()

    private lateinit var subject: FetchCryptoRepositoryImpl
    private lateinit var result: CryptoServiceGetCoinsResponse

    @Before
    fun setUp() {
        subject = FetchCryptoRepositoryImpl(
            mockService
        )
    }

    @Test
    fun requestCryptoInformationTest_serviceReturnsSuccess() {
        coEvery { mockService.getCoins() } returns Response.success(mockCryptoServiceGetCoinsResponse)

        runBlocking {
            result = subject.requestCryptoInformationTest()
        }

        assertEquals(mockCryptoServiceGetCoinsResponse, result)
    }

    @Test
    fun requestCryptoInformationTest_serviceReturnsSuccessWithNullBody() {
        coEvery { mockService.getCoins() } returns Response.success(null)

        runBlocking {
            result = subject.requestCryptoInformationTest()
        }

        assertEquals(CryptoServiceGetCoinsResponse("testFailed - body was null", null), result)
    }

    @Test
    fun requestCryptoInformationTest_serviceDoesNotReturnsSuccess() {
        val mockErrorResponseBody = mockk<ResponseBody>().also {
            every { it.contentType() } returns mockk()
            every { it.contentLength() } returns 15L
        }
        coEvery { mockService.getCoins() } returns Response.error(404, mockErrorResponseBody)

        runBlocking {
            result = subject.requestCryptoInformationTest()
        }

        assertEquals(CryptoServiceGetCoinsResponse("testFailed - request unsuccessful", null), result)
    }

    @Test
    fun requestCryptoInformationTest_serviceReturnsNullResponse() {
        coEvery { mockService.getCoins() } returns null

        runBlocking {
            result = subject.requestCryptoInformationTest()
        }

        assertEquals(CryptoServiceGetCoinsResponse("testFailed - response was null", null), result)
    }
}
