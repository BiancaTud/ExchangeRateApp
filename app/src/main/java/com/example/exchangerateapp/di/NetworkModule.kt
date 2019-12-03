package com.example.exchangerateapp.di

import android.util.Log
import com.example.exchangerateapp.BuildConfig
import com.example.exchangerateapp.data.retrofit.RatesApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val API_DATE_FORMAT = "yyyy-MM-dd hh:mm"

val networkModule = module {

    factory<Interceptor> {
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    factory { OkHttpClient.Builder().addInterceptor(get()).build() }


    factory {
        GsonBuilder()
            .setDateFormat(API_DATE_FORMAT)
            .create()
    }

    single {
        provideRetrofit(get(), get())
    }

    factory {
        provideRatesApi(get())
    }


}

fun provideRetrofit(okHttpClient: OkHttpClient, gsonFactory: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(gsonFactory)
        )
        .build()
}

fun provideRatesApi(retrofit: Retrofit): RatesApi = retrofit.create(RatesApi::class.java)

