package com.example.exchangerateapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exchangerateapp.data.RatesRepository
import com.example.exchangerateapp.data.model.Rate
import com.example.exchangerateapp.ui.BaseViewModel
import com.example.exchangerateapp.util.DebugResponseLogger
import com.example.exchangerateapp.util.getLongDateFormat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import io.reactivex.disposables.Disposable


class HomeViewModel(private val repository: RatesRepository) : BaseViewModel() {

    private val _ratesList = MutableLiveData<List<Rate>>()
    val ratesList: LiveData<List<Rate>>
        get() = _ratesList
    private val _timestamp = MutableLiveData<String>()
    val timestamp: LiveData<String>
        get() = _timestamp

    private var pollingDisposable: Disposable? = null

    fun getExchangeRatesFor(currentCurrency:String, refreshValue:Long) {
        showProgressEvent.call()
        resumePooling(currentCurrency, refreshValue)
    }


    private fun resumePooling(currentCurrency:String, refreshValue:Long) {
        if (pollingDisposable == null) {
            pollingDisposable =
                repository.getExchangeRates(currentCurrency).subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread()
                ).repeatWhen { observable -> observable.delay(refreshValue, TimeUnit.SECONDS) } // refresh rates from server every "refreshValue" seconds
                    .subscribe({ response ->
                        _ratesList.postValue(response.rates.map { Rate(it.key, it.value) }) //format response received from server
                        _timestamp.postValue(getCurrentTimestamp())
                        DebugResponseLogger.log("getExchangeRatesFor", response.toString())
                    },
                        {
                            showErrorConnectionEvent.postValue(it.localizedMessage)
                            DebugResponseLogger.log("getExchangeRatesFor", it.toString())
                        })

            registerDisposable(pollingDisposable as Disposable)
        }
    }


    fun stopPolling() {
        pollingDisposable?.let { disposable ->
            disposable.dispose()
            unregisterDisposable(disposable)
        }
        pollingDisposable = null

    }


    private fun getCurrentTimestamp(): String {
        return getLongDateFormat(System.currentTimeMillis())
    }


}