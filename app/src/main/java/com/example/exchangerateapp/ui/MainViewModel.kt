package com.example.exchangerateapp.ui

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var currenciesList : MutableList<String> = emptyList<String>().toMutableList()
    var currentRefreshValue = 3L
    var currentCurrency = "EUR"

    companion object {
        val refreshValuesList = arrayOf(3L, 5L, 15L)
    }



}