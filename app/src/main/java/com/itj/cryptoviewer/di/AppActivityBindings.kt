package com.itj.cryptoviewer.di

import com.itj.cryptoviewer.view.cryptolist.CryptoViewerActivity
import com.itj.cryptoviewer.view.cryptolist.di.CryptoViewerViewModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppActivityBindings {

    @ActivityScope
    @ContributesAndroidInjector(modules = [CryptoViewerViewModule::class])
    abstract fun provideCryptoViewerActivity(): CryptoViewerActivity
}
