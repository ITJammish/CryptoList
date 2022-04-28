package com.itj.cryptoviewer.data.mapper

import android.util.Log
import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.data.model.GetCoinsCoin
import com.itj.cryptoviewer.domain.model.Coin
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Test

class NetworkCoinToDomainCoinMapperTest {

    private companion object {
        const val UUID = "uuid"
        const val SYMBOL = "symbol"
        const val NAME = "name"
        const val COLOR = "color"
        const val ICON_URL = "iconUrl"
        const val PRICE = "price"
        const val OVERFLOW_PRICE = "1.23456789"
        const val EXPECTED_CONCAT_PRICE = "1.23"
        const val CHANGE = "change"
        const val OVERFLOW_CHANGE = "2.23456789"
        const val EXPECTED_CONCAT_CHANGE = "2.23"
        const val SPARKLINE = "sparkline"
        const val COIN_RANKING_URL = "coinrankingUrl"
    }

    private val mockNetCoin = mockk<GetCoinsCoin>().also {
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

    private lateinit var subject: NetworkCoinToDomainCoinMapper
    private lateinit var result: Coin

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e("CryptoSummaryRecyclerViewAdapter", any()) } returns 0

        subject = NetworkCoinToDomainCoinMapper()
    }

    @Test
    fun mapNetworkCoinToDomainCoin() {
        result = subject.mapNetworkCoinToDomainCoin(mockNetCoin)
        assertThat(result).isEqualTo(mockDomainCoin)
    }

    @Test
    fun mapNetworkCoinToDomainCoin_withOverFlowValues() {
        every { mockNetCoin.price } returns OVERFLOW_PRICE
        every { mockDomainCoin.price } returns EXPECTED_CONCAT_PRICE
        every { mockNetCoin.change } returns OVERFLOW_CHANGE
        every { mockDomainCoin.change } returns EXPECTED_CONCAT_CHANGE

        result = subject.mapNetworkCoinToDomainCoin(mockNetCoin)

        assertThat(result).isEqualTo(mockDomainCoin)
    }
}
