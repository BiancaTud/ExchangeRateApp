package com.example.exchangerateapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exchangerateapp.data.RatesRepository
import com.example.exchangerateapp.data.model.Rate
import com.example.exchangerateapp.data.model.Rates
import com.example.exchangerateapp.ui.BaseViewModel
import com.example.exchangerateapp.util.DebugResponseLogger
import com.example.exchangerateapp.util.getDateFormat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import io.reactivex.disposables.Disposable


class HomeViewModel(private val repository: RatesRepository) : BaseViewModel() {

    val base = "EUR"
    private val _ratesList = MutableLiveData<List<Rate>>()
    val ratesList: LiveData<List<Rate>>
        get() = _ratesList
    private val _timestamp = MutableLiveData<String>()
    val timestamp: LiveData<String>
        get() = _timestamp

    val delay: Long = 3

    private var pollingDisposable: Disposable? = null

    fun getExchangeRatesFor(base: String = HomeViewModel@ this.base) {
        resumePooling(base)
    }


    private fun resumePooling(base: String) {
        if (pollingDisposable == null) {
            pollingDisposable =
                repository.getSearchResults(base).subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread()
                ).repeatWhen { observable -> observable.delay(delay, TimeUnit.SECONDS) }
                    .subscribe({ response ->
                        _ratesList.postValue(response.rates.map { Rate(it.key, it.value) })
                        _timestamp.postValue(getCurrentTimestamp())
                        DebugResponseLogger.log("getExchangeRatesFor", response.toString())
                    },
                        {
                            showErrorConnectionEvent.call()
                            DebugResponseLogger.log("getExchangeRatesFor", it.toString())
                        })

            registerDisposable(pollingDisposable as Disposable)
        }
    }


    fun stopPooling() {
        pollingDisposable?.let { disposable ->
            disposable.dispose()
            unregisterDisposable(disposable)
        }
        pollingDisposable = null

    }


    private fun getCurrentTimestamp(): String {
        return getDateFormat(System.currentTimeMillis())
    }


}