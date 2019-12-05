package com.example.exchangerateapp.data

import com.example.exchangerateapp.data.response.ExchangeRatesResponse
import com.example.exchangerateapp.data.response.HistoryRatesResponse
import com.example.exchangerateapp.data.retrofit.RatesApi
import io.reactivex.Single

class RatesRepository(private val ratesApi: RatesApi) {

    fun getExchangeRates(base: String): Single<ExchangeRatesResponse> {
        return ratesApi.getExchangeRates(base)
    }

    fun getHistoryRates(base: String, startAt:String, endAt:String, symbols:String): Single<HistoryRatesResponse> {
        return ratesApi.getHistoryRates(base, startAt, endAt, symbols)
    }
}