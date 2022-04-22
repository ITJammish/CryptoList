package com.itj.cryptoviewer.cryptolist.di

import com.itj.cryptoviewer.cryptolist.CryptoListActivity
import com.itj.cryptoviewer.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface CryptoListActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CryptoListActivityComponent
    }

    fun inject(activity: CryptoListActivity)
}
