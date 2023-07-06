package com.az.tmdbshowlist

import android.app.Application
import com.az.tmdbshowlist.di.dataModule
import com.az.tmdbshowlist.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class TVSApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@TVSApplication)
            modules(dataModule, uiModule)
        }
    }
}