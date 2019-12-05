package com.example.exchangerateapp.data.retrofit

import com.example.exchangerateapp.data.response.ExchangeRatesResponse
import com.example.exchangerateapp.data.response.HistoryRatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

    @GET("latest")
    fun getExchangeRates(@Query("base") base: String): Single<ExchangeRatesResponse>

    @GET("history")
    fun getHistoryRates(
        @Query("base") base: String,
        @Query("start_at") startAt: String,
        @Query("end_at") endAt: String,
        @Query("symbols") symbols: String
    ): Single<HistoryRatesResponse>
}