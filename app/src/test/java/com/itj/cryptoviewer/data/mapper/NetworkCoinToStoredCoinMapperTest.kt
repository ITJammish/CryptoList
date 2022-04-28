package com.itj.cryptoviewer.data.mapper

import android.util.Log
import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.data.database.StoredCoin
import com.itj.cryptoviewer.data.model.GetCoinsCoin
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Test

class NetworkCoinToStoredCoinMapperTest {

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
        const val RANK = 1
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
        every { it.rank } returns RANK
        every { it.sparkline } returns listOf(SPARKLINE)
        every { it.coinrankingUrl } returns COIN_RANKING_URL
    }

    private val mockStoredCoin = mockk<StoredCoin>().also {
        every { it.uuid } returns UUID
        every { it.symbol } returns SYMBOL
        every { it.name } returns NAME
        every { it.color } returns COLOR
        every { it.iconUrl } returns ICON_URL
        every { it.price } returns PRICE
        every { it.change } returns CHANGE
        every { it.rank } returns RANK
        every { it.sparkline } returns listOf(SPARKLINE)
        every { it.coinrankingUrl } returns COIN_RANKING_URL
    }

    private lateinit var subject: NetworkCoinToStoredCoinMapper
    private lateinit var result: StoredCoin

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e("CryptoSummaryRecyclerViewAdapter", any()) } returns 0

        subject = NetworkCoinToStoredCoinMapper()
    }

    @Test
    fun mapNetworkCoinToStoredCoin() {
        result = subject.mapNetworkCoinToStoredCoin(mockNetCoin)
        assertThat(result).isEqualTo(mockStoredCoin)
    }

    @Test
    fun mapNetworkCoinToStoredCoin_withOverflowValues() {
        every { mockNetCoin.price } returns OVERFLOW_PRICE
        every { mockStoredCoin.price } returns EXPECTED_CONCAT_PRICE
        every { mockNetCoin.change } returns OVERFLOW_CHANGE
        every { mockStoredCoin.change } returns EXPECTED_CONCAT_CHANGE

        result = subject.mapNetworkCoinToStoredCoin(mockNetCoin)
        assertThat(result).isEqualTo(mockStoredCoin)
    }
}
