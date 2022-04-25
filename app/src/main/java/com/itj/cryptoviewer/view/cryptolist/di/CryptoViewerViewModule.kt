package com.itj.cryptoviewer.view.cryptolist.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itj.cryptoviewer.di.viewmodel.ViewModelFactory
import com.itj.cryptoviewer.di.viewmodel.ViewModelKey
import com.itj.cryptoviewer.view.cryptolist.CryptoListViewModel
import com.itj.cryptoviewer.view.cryptolist.CryptoSummaryRecyclerViewAdapter
import com.itj.cryptoviewer.view.cryptolist.CryptoViewerActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

@Module
abstract class CryptoViewerViewModule {

    @Named(SCOPE_FOR_CRYPTO_VIEWER_ACTIVITY)
    @Binds
    abstract fun bindContext(activity: CryptoViewerActivity): Context

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CryptoListViewModel::class)
    abstract fun provideCryptoListViewModel(viewModel: CryptoListViewModel): ViewModel

    companion object {
        private const val SCOPE_FOR_CRYPTO_VIEWER_ACTIVITY = "ForCryptoViewerActivity"

        @Provides
        fun provideCryptoSummaryRecyclerViewAdapter(): CryptoSummaryRecyclerViewAdapter =
            CryptoSummaryRecyclerViewAdapter()

        @Provides
        fun provideLayoutManager(@Named(SCOPE_FOR_CRYPTO_VIEWER_ACTIVITY) context: Context): RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}
