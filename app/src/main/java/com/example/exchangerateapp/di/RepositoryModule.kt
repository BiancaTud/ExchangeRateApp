package com.example.exchangerateapp.di

import com.example.exchangerateapp.data.RatesRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { RatesRepository(get()) }
}