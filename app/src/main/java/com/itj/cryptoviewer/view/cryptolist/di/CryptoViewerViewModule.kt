package com.itj.cryptoviewer.view.cryptolist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itj.cryptoviewer.di.viewmodel.ViewModelFactory
import com.itj.cryptoviewer.di.viewmodel.ViewModelKey
import com.itj.cryptoviewer.view.cryptolist.CryptoListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class CryptoViewerViewModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CryptoListViewModel::class)
    abstract fun provideCryptoListViewModel(viewModel: CryptoListViewModel): ViewModel
}
