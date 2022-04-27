package com.itj.cryptoviewer.view.cryptolist.list

import com.google.common.truth.Truth.assertThat
import com.itj.cryptoviewer.domain.model.Coin
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CryptoSummaryDiffUtilCallbackTest {

    private companion object {
        const val STUB_SIZE = 5
        const val STUB_UUID = "stub_uuid"
        const val STUB_UUID_ALT = "stub_uuid_alt"
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
}
