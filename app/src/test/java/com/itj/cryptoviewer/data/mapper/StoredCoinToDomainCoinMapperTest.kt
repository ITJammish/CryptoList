package com.itj.cryptoviewer.data.mapper

import com.google.common.truth.Truth
import com.itj.cryptoviewer.data.database.StoredCoin
import com.itj.cryptoviewer.domain.model.Coin
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class StoredCoinToDomainCoinMapperTest {

    private companion object {
        const val UUID = "uuid"
        const val SYMBOL = "symbol"
        const val NAME = "name"
        const val COLOR = "color"
        const val ICON_URL = "iconUrl"
        const val PRICE = "price"
        const val CHANGE = "change"
        const val SPARKLINE = "sparkline"
        const val COIN_RANKING_URL = "coinrankingUrl"
    }

    private val mockStoredCoin = mockk<StoredCoin>().also {
        every { it.uuid } returns UUID
        every { it.symbol } returns SYMBOL
        every { it.name } returns NAME
        every { it.color } returns COLOR
        every { it.iconUrl } returns ICON_URL
        every { it.price } returns PRICE
        every { it.change } returns CHANGE
        every { it.sparkline } returns listOf(SPARKLINE)
        every { it.coinrankingUrl } returns COIN_RANKING_URL
    }

    private val mockDomainCoin = mockk<Coin>().also {
        every { it.uuid } returns UUID
        every { it.symbol } returns SYMBOL
        every { it.name } returns NAME
        every { it.color } returns COLOR
        every { it.iconUrl } returns ICON_URL
        every { it.price } returns PRICE
        every { it.change } returns CHANGE
        every { it.sparkline } returns listOf(SPARKLINE)
        every { it.coinrankingUrl } returns COIN_RANKING_URL
    }

    private lateinit var subject: StoredCoinToDomainCoinMapper
    private lateinit var result: Coin

    @Before
    fun setUp() {
        subject = StoredCoinToDomainCoinMapper()
    }

    @Test
    fun `mapStoredCoinToDomainCoin$app_debug`() {
        result = subject.mapStoredCoinToDomainCoin(mockStoredCoin)
        Truth.assertThat(result).isEqualTo(mockDomainCoin)
    }
}
