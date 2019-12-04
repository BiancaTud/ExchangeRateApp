package com.example.exchangerateapp

import android.app.Application
import com.example.exchangerateapp.di.networkModule
import com.example.exchangerateapp.di.repositoryModule
import com.example.exchangerateapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ExchangesRateApplication: Application() {

    private val appModule = mutableListOf(networkModule, repositoryModule, viewModelModule)

    override fun onCreate() {
        super.onCreate()
        startKoin{
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()
            // use the Android context given there
            androidContext(this@ExchangesRateApplication)
            modules(appModule)
        }
    }
}