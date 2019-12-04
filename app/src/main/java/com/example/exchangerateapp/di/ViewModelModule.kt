package com.example.exchangerateapp.di

import com.example.exchangerateapp.data.RatesRepository
import com.example.exchangerateapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get<RatesRepository>()) }
}