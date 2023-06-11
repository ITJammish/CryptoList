package com.itj.cryptoviewer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.itj.cryptoviewer.rules.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)

abstract class ViewModelTestBase {

    @get:Rule
    val mockKRule = MockKRule()

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    internal suspend fun <T> Flow<T>.test(block: suspend (List<T>) -> Unit) = runTest {
        val results = mutableListOf<T>()
        val job = launch { this@test.toList(results) }
        block(results)
        job.cancel()
    }
}