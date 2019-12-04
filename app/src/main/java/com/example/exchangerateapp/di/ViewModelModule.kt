package com.example.exchangerateapp.di

import com.example.exchangerateapp.ui.BaseViewModel
import com.example.exchangerateapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BaseViewModel() }
    viewModel { HomeViewModel(get()) }
}