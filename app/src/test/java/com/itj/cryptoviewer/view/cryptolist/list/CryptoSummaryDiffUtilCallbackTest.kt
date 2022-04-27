package com.itj.cryptoviewer.view.cryptolist.list

import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.domain.model.Coin
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class CryptoSummaryDiffUtilCallbackTest {

    private companion object {
        const val STUB_SIZE = 5
        const val STUB_UUID = "stub_uuid"
        const val STUB_UUID_ALT = "stub_uuid_alt"
        const val STUB_CHANGE = "stub_change"
        const val STUB_CHANGE_ALT = "stub_change_alt"
        const val STUB_PRICE = "stub_price"
        const val STUB_PRICE_ALT = "stub_price_alt"
    }

    private val mockOldList = mockk<List<Coin>>()
    private val mockNewList = mockk<List<Coin>>()

    private lateinit var subject: CryptoSummaryDiffUtilCallback

    @Test
    fun getOldListSize() {
        every { mockOldList.size } returns STUB_SIZE
        subject = CryptoSummaryDiffUtilCallback(mockOldList, mockNewList)

        val result = subject.oldListSize

        assertThat(result).isEqualTo(STUB_SIZE)
    }

    @Test
    fun getNewListSize() {
        every { mockNewList.size } returns STUB_SIZE
        subject = CryptoSummaryDiffUtilCallback(mockOldList, mockNewList)

        val result = subject.newListSize

        assertThat(result).isEqualTo(STUB_SIZE)
    }

    @Test
    fun areItemsTheSame_whenUuidMatches_returnsTrue() {
        every { mockOldList[0].uuid } returns STUB_UUID
        every { mockNewList[0].uuid } returns STUB_UUID
        subject = CryptoSummaryDiffUtilCallback(mockOldList, mockNewList)

        val result = subject.areItemsTheSame(0, 0)

        assertTrue(result)
    }

    @Test
    fun areItemsTheSame_whenUuidDoesNotMatch_returnsFalse() {
        every { mockOldList[0].uuid } returns STUB_UUID
        every { mockNewList[0].uuid } returns STUB_UUID_ALT
        subject = CryptoSummaryDiffUtilCallback(mockOldList, mockNewList)

        val result = subject.areItemsTheSame(0, 0)

        assertFalse(result)
    }

    @Test
    fun areContentsTheSame_whenContentsMatch_returnsTrue() {
        every { mockOldList[0] == (mockNewList[0]) } returns true
        subject = CryptoSummaryDiffUtilCallback(mockOldList, mockNewList)

        val result = subject.areContentsTheSame(0, 0)

        assertTrue(result)
    }

    @Test
    fun areContentsTheSame_whenContentsDoNotMatch_returnsFalse() {
        every { mockOldList[0] == (mockNewList[0]) } returns false
        subject = CryptoSummaryDiffUtilCallback(mockOldList, mockNewList)

        val result = subject.areContentsTheSame(0, 0)

        assertFalse(result)
    }

    @Test
    fun getChangePayload_whenChangeMatchesAndPriceMatches_returnsNull() {
        every { mockOldList[0].change } returns STUB_CHANGE
        every { mockNewList[0].change } returns STUB_CHANGE
        every { mockOldList[0].price } returns STUB_PRICE
        every { mockNewList[0].price } returns STUB_PRICE
        subject = CryptoSummaryDiffUtilCallback(mockOldList, mockNewList)

        val result = subject.getChangePayload(0, 0)

        assertNull(result)
    }

    @Test
    fun getChangePayload_whenChangeDoesNotMatchAndPriceDoesNotMatch_returnsListOfUpdateChangeAndUpdatePrice() {
        every { mockOldList[0].change } returns STUB_CHANGE
        every { mockNewList[0].change } returns STUB_CHANGE_ALT
        every { mockOldList[0].price } returns STUB_PRICE
        every { mockNewList[0].price } returns STUB_PRICE_ALT
        subject = CryptoSummaryDiffUtilCallback(mockOldList, mockNewList)

        val result = subject.getChangePayload(0, 0)

        assertThat(result).isEqualTo(
            mutableListOf(
                CryptoSummaryDiffUtilCallback.ChangePayload.UpdateChange,
                CryptoSummaryDiffUtilCallback.ChangePayload.UpdatePrice
            )
        )
    }

    @Test
    fun getChangePayload_whenChangeDoesNotMatchAndPriceMatches_returnsListOfUpdateChangeOnly() {
        every { mockOldList[0].change } returns STUB_CHANGE
        every { mockNewList[0].change } returns STUB_CHANGE_ALT
        every { mockOldList[0].price } returns STUB_PRICE
        every { mockNewList[0].price } returns STUB_PRICE
        subject = CryptoSummaryDiffUtilCallback(mockOldList, mockNewList)

        val result = subject.getChangePayload(0, 0)

        assertThat(result).isEqualTo(
            mutableListOf(
                CryptoSummaryDiffUtilCallback.ChangePayload.UpdateChange
            )
        )
    }

    @Test
    fun getChangePayload_whenChangeMatchesAndPriceDoesNotMatch_returnsListOfUpdatePriceOnly() {
        every { mockOldList[0].change } returns STUB_CHANGE
        every { mockNewList[0].change } returns STUB_CHANGE
        every { mockOldList[0].price } returns STUB_PRICE
        every { mockNewList[0].price } returns STUB_PRICE_ALT
        subject = CryptoSummaryDiffUtilCallback(mockOldList, mockNewList)

        val result = subject.getChangePayload(0, 0)

        assertThat(result).isEqualTo(
            mutableListOf(
                CryptoSummaryDiffUtilCallback.ChangePayload.UpdatePrice
            )
        )
    }
}
