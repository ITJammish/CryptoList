package com.itj.cryptoviewer.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.data.repository.CryptoListDataRepository
import com.itj.cryptoviewer.domain.model.Coin
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import org.junit.Before
import org.junit.Test

class ConnectViewToDataSourceTest {

    private val mockFlow = mockk<Flow<List<Coin>>>()
    private val mockCryptoListDataRepository = mockk<CryptoListDataRepository>().also {
        coEvery { it.cryptoListData } returns mockFlow
    }

    private lateinit var subject: ConnectViewToDataSource
    private lateinit var result: Flow<List<Coin>>

    @Before
    fun setUp() {
        subject = ConnectViewToDataSource(
            mockCryptoListDataRepository
        )
    }

    @Test
    operator fun invoke() {
        result = subject.invoke()
        assertThat(result).isEqualTo(mockFlow)
    }
}
