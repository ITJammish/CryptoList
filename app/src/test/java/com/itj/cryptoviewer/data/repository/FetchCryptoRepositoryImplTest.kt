package com.itj.cryptoviewer.data.repository

import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.data.CryptoService
import com.itj.cryptoviewer.data.database.CoinDao
import com.itj.cryptoviewer.data.database.StoredCoin
import com.itj.cryptoviewer.data.mapper.NetworkCoinToDomainCoinMapper
import com.itj.cryptoviewer.data.mapper.NetworkCoinToStoredCoinMapper
import com.itj.cryptoviewer.data.model.CryptoServiceGetCoinsResponse
import com.itj.cryptoviewer.data.model.GetCoinsCoin
import com.itj.cryptoviewer.data.utils.Resource
import com.itj.cryptoviewer.data.utils.ResourceErrorType
import com.itj.cryptoviewer.domain.model.Coin
import io.mockk.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class FetchCryptoRepositoryImplTest {

    private companion object {
        const val NET_COIN_NAME = "net_coin_name"
    }

    private val mockDomainCoin = mockk<Coin>()
    private val mockStoredCoin = mockk<StoredCoin>()
    private val mockNetCoin = mockk<GetCoinsCoin>().also {
        every { it.name } returns NET_COIN_NAME
    }
    private val mockCryptoServiceGetCoinsResponse = mockk<CryptoServiceGetCoinsResponse>()
    private val mockkFlow = mockk<Flow<List<StoredCoin>>>()

    private val mockService = mockk<CryptoService>()
    private val mockNetworkCoinToDomainCoinMapper = mockk<NetworkCoinToDomainCoinMapper>()
    private val mockNetworkCoinToStoredCoinMapper = mockk<NetworkCoinToStoredCoinMapper>()
    private val mockCoinDao = mockk<CoinDao>().also {
        every { it.getRankedCoins() } returns mockkFlow
        coEvery { it.insert(mockStoredCoin) } returns Unit
    }

    private lateinit var subject: FetchCryptoRepositoryImpl
    private lateinit var result: Resource<List<Coin>>

    @Before
    fun setUp() {
        subject = FetchCryptoRepositoryImpl(
            mockService,
            mockNetworkCoinToDomainCoinMapper,
            mockNetworkCoinToStoredCoinMapper,
            mockCoinDao,
        )
        every { mockNetworkCoinToDomainCoinMapper.mapNetworkCoinToDomainCoin(mockNetCoin) } returns mockDomainCoin
        every { mockNetworkCoinToStoredCoinMapper.mapNetworkCoinToStoredCoin(mockNetCoin) } returns mockStoredCoin
    }

    @Test
    fun subjectInitTest() {
        verify { mockCoinDao.getRankedCoins() }
        assertThat(subject.coins).isEqualTo(mockkFlow)
    }

    @Test
    fun requestCryptoInformationTest_serviceReturnsSuccess() {
        every { mockCryptoServiceGetCoinsResponse.data?.coins } returns listOf(mockNetCoin)
        coEvery { mockService.getCoins() } returns Response.success(mockCryptoServiceGetCoinsResponse)

        runBlocking {
            result = subject.requestCryptoInformation()
        }

        verify {
            mockNetworkCoinToDomainCoinMapper.mapNetworkCoinToDomainCoin(mockNetCoin)
            mockNetworkCoinToStoredCoinMapper.mapNetworkCoinToStoredCoin(mockNetCoin)
        }
        coVerify {
            mockCoinDao.insert(mockStoredCoin)
        }
        assertThat(result).isEqualTo(Resource.Success(listOf(mockDomainCoin)))
    }

    @Test
    fun requestCryptoInformationTest_serviceReturnsSuccessWithNullData() {
        every { mockCryptoServiceGetCoinsResponse.data } returns null
        coEvery { mockService.getCoins() } returns Response.success(mockCryptoServiceGetCoinsResponse)

        runBlocking {
            result = subject.requestCryptoInformation()
        }

        verify(exactly = 0) { mockNetworkCoinToDomainCoinMapper.mapNetworkCoinToDomainCoin(mockNetCoin) }
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
