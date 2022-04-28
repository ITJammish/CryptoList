package com.itj.cryptoviewer.data

import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.data.mapper.NetworkCoinToDomainCoinMapper
import com.itj.cryptoviewer.data.model.CryptoServiceGetCoinsResponse
import com.itj.cryptoviewer.data.model.GetCoinsCoin
import com.itj.cryptoviewer.data.repository.FetchCryptoRepositoryImpl
import com.itj.cryptoviewer.data.utils.Resource
import com.itj.cryptoviewer.data.utils.ResourceErrorType
import com.itj.cryptoviewer.domain.model.Coin
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

// todo update
class FetchCryptoRepositoryImplTest {

    private companion object {
        const val NET_COIN_NAME = "net_coin_name"
    }

    private val mockDomainCoin = mockk<Coin>()
    private val mockNetCoin = mockk<GetCoinsCoin>().also {
        every { it.name } returns NET_COIN_NAME
    }
    private val mockCryptoServiceGetCoinsResponse = mockk<CryptoServiceGetCoinsResponse>()

    private val mockService = mockk<CryptoService>()
    private val mockMapper = mockk<NetworkCoinToDomainCoinMapper>()

    private lateinit var subject: FetchCryptoRepositoryImpl
    private lateinit var result: Resource<List<Coin>>

    @Before
    fun setUp() {
        subject = FetchCryptoRepositoryImpl(
            mockService,
            mockMapper
        )
        every { mockMapper.mapNetworkCoinToDomainCoin(mockNetCoin) } returns mockDomainCoin
    }

    @Test
    fun requestCryptoInformationTest_serviceReturnsSuccess() {
        every { mockCryptoServiceGetCoinsResponse.data?.coins } returns listOf(mockNetCoin)
        coEvery { mockService.getCoins() } returns Response.success(mockCryptoServiceGetCoinsResponse)

        runBlocking {
            result = subject.requestCryptoInformation()
        }

        verify { mockMapper.mapNetworkCoinToDomainCoin(mockNetCoin) }
        assertThat(result).isEqualTo(Resource.Success(listOf(mockDomainCoin)))
    }

    @Test
    fun requestCryptoInformationTest_serviceReturnsSuccessWithNullData() {
        every { mockCryptoServiceGetCoinsResponse.data } returns null
        coEvery { mockService.getCoins() } returns Response.success(mockCryptoServiceGetCoinsResponse)

        runBlocking {
            result = subject.requestCryptoInformation()
        }

        verify(exactly = 0) { mockMapper.mapNetworkCoinToDomainCoin(mockNetCoin) }
        assertThat(result).isEqualTo(Resource.Error(ResourceErrorType.Generic))
    }

    @Test
    fun requestCryptoInformationTest_serviceReturnsSuccessWithNullBody() {
        coEvery { mockService.getCoins() } returns Response.success(null)

        runBlocking {
            result = subject.requestCryptoInformation()
        }

        assertThat(result).isEqualTo(Resource.Error(ResourceErrorType.Generic))
    }

    @Test
    fun requestCryptoInformationTest_serviceDoesNotReturnSuccess() {
        val mockErrorResponseBody = mockk<ResponseBody>().also {
            every { it.contentType() } returns mockk()
            every { it.contentLength() } returns 15L
        }
        coEvery { mockService.getCoins() } returns Response.error(404, mockErrorResponseBody)

        runBlocking {
            result = subject.requestCryptoInformation()
        }

        assertThat(result).isEqualTo(Resource.Error(ResourceErrorType.Connection))
    }

    @Test
    fun requestCryptoInformationTest_serviceReturnsNullResponse() {
        coEvery { mockService.getCoins() } returns null

        runBlocking {
            result = subject.requestCryptoInformation()
        }

        assertThat(result).isEqualTo(Resource.Error(ResourceErrorType.Generic))
    }
}
