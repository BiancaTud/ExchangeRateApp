package com.example.exchangerateapp.data

import com.example.exchangerateapp.data.model.ExchangeRatesResponse
import com.example.exchangerateapp.data.retrofit.RatesApi
import io.reactivex.Single

class RatesRepository(private val ratesApi: RatesApi) {

    fun getSearchResults(base: String): Single<ExchangeRatesResponse> {
        return ratesApi.getExchangeRates(base)
    }
}