package com.example.exchangerateapp.data.model

data class ExchangeRatesResponse(val rates: Map<String, Double>, val base: String, val date: String)