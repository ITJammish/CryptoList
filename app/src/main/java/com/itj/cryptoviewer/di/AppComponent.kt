package com.itj.cryptoviewer.di

import android.app.Application
import com.itj.cryptoviewer.CryptoViewerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        AppActivityBindings::class,
        DataModule::class,
    ]
)
interface AppComponent : AndroidInjector<CryptoViewerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
