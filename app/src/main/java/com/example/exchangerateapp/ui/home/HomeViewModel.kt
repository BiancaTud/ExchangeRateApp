package com.example.exchangerateapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exchangerateapp.data.RatesRepository
import com.example.exchangerateapp.data.model.Rates
import com.example.exchangerateapp.ui.BaseViewModel
import com.example.exchangerateapp.util.DebugResponseLogger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: RatesRepository) : BaseViewModel() {

    val base = "EUR"
    private val _ratesList = MutableLiveData<Rates>()
    private val ratesList: LiveData<Rates>
        get() = _ratesList

    fun getExchangeRatesFor(base: String=HomeViewModel@this.base) {
        registerDisposable(
            repository.getSearchResults(base).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({
                _ratesList.value =
                    it.rates
                DebugResponseLogger.log("getExchangeRatesFor", it.toString())
            },
                {

                    DebugResponseLogger.log("getExchangeRatesFor", it.toString())
                })
        )

    }

}