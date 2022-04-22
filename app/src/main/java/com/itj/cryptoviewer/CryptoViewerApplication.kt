package com.itj.cryptoviewer

import android.app.Application
import com.itj.cryptoviewer.di.AppComponent
import com.itj.cryptoviewer.di.DaggerAppComponent

open class CryptoViewerApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}
