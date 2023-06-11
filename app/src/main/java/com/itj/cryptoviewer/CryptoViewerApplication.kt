package com.itj.cryptoviewer

import com.itj.cryptoviewer.di.AppComponent
import com.itj.cryptoviewer.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class CryptoViewerApplication : DaggerApplication() {

    lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build().also {
            appComponent = it
        }
}
