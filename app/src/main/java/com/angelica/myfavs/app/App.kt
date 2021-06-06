package com.angelica.myfavs.app

import android.app.Application
import com.angelica.myfavs.dependency_injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                roomDataBaseModule,
                repositoryModule,
                viewModelModule,
                retrofitModule
            )
        }
    }
}