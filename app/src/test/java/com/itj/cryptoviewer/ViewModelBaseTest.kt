package com.itj.cryptoviewer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.itj.cryptoviewer.rules.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule

abstract class ViewModelTestBase {

    @get:Rule
    val mockKRule = MockKRule()

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()
}