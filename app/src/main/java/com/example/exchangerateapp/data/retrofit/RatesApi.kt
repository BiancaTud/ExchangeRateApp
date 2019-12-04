package com.example.exchangerateapp.data.retrofit

import com.example.exchangerateapp.data.model.ExchangeRatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

    @GET("latest")
    fun getExchangeRates(@Query("base") base: String): Single<ExchangeRatesResponse>
}