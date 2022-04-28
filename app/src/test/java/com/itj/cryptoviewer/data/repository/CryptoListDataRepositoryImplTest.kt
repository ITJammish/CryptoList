package com.itj.cryptoviewer.data.repository

import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.data.database.StoredCoin
import com.itj.cryptoviewer.data.mapper.StoredCoinToDomainCoinMapper
import com.itj.cryptoviewer.domain.model.Coin
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CryptoListDataRepositoryImplTest {

    private val mockStoredCoin = mockk<StoredCoin>()
    private val mockDomainCoin = mockk<Coin>()

    private val mockFetchCryptoRepository = mockk<FetchCryptoRepository>().also {
        coEvery { it.coins } returns flow {
            emit(listOf(mockStoredCoin))
        }
    }
    private val mockStoredCoinToDomainCoinMapper = mockk<StoredCoinToDomainCoinMapper>().also {
        every { it.mapStoredCoinToDomainCoin(mockStoredCoin) } returns mockDomainCoin
    }

    private lateinit var subject: CryptoListDataRepositoryImpl
    private lateinit var result: List<Coin>

    @Before
    fun setUp() {
        subject = CryptoListDataRepositoryImpl(
            mockFetchCryptoRepository,
            mockStoredCoinToDomainCoinMapper,
        )
    }

    @Test
    fun getCryptoListData() {
        runBlocking {
            result = subject.cryptoListData.first()
        }

        verify { mockStoredCoinToDomainCoinMapper.mapStoredCoinToDomainCoin(mockStoredCoin) }
        assertThat(result[0]).isEqualTo(mockDomainCoin)
    }
}
