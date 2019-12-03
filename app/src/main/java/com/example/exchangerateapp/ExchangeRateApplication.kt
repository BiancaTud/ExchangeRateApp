package com.example.exchangerateapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ExchangeRateApplication: Application() {

    private val appModule = module { }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()
            // use the Android context given there
            androidContext(this@ExchangeRateApplication)
            modules(appModule)
        }
    }
}