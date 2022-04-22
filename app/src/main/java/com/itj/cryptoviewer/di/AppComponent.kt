package com.itj.cryptoviewer.di

import android.content.Context
import com.itj.cryptoviewer.cryptolist.di.CryptoListActivityComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun cryptoListComponent(): CryptoListActivityComponent.Factory
}
