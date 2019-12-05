package com.example.exchangerateapp.di

import com.example.exchangerateapp.ui.BaseViewModel
import com.example.exchangerateapp.ui.MainViewModel
import com.example.exchangerateapp.ui.history.HistoryViewModel
import com.example.exchangerateapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BaseViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { HistoryViewModel(get()) }
}