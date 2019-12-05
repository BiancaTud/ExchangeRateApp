package com.example.exchangerateapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exchangerateapp.data.RatesRepository
import com.example.exchangerateapp.data.response.HistoryRatesResponse
import com.example.exchangerateapp.ui.BaseViewModel
import com.example.exchangerateapp.util.DebugResponseLogger
import com.example.exchangerateapp.util.getDateFormatForApi
import com.example.exchangerateapp.util.getStartDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class HistoryViewModel(private val repository: RatesRepository) : BaseViewModel() {

    companion object {
        const val currencyRON = "RON"
        const val currencyUSD = "USD"
        const val currencyBGN = "BGN"
        const val days = 10
    }

    private val currentTimeMillis = Calendar.getInstance().timeInMillis
    private var _ronDataSet = MutableLiveData<List<Pair<Date, Double>>>()
    val ronDataSet: LiveData<List<Pair<Date, Double>>>
        get() = _ronDataSet
    private var _usdDataSet = MutableLiveData<List<Pair<Date, Double>>>()
    val usdDataSet: LiveData<List<Pair<Date, Double>>>
        get() = _usdDataSet
    private var _bgnDataSet = MutableLiveData<List<Pair<Date, Double>>>()
    val bgnDataSet: LiveData<List<Pair<Date, Double>>>
        get() = _bgnDataSet


    fun getHistoryRatesFor(currentCurrency: String) {
        registerDisposableFor(currentCurrency, currencyRON, _ronDataSet)
        registerDisposableFor(currentCurrency, currencyUSD, _usdDataSet)
        registerDisposableFor(currentCurrency, currencyBGN, _bgnDataSet)

    }

    private fun registerDisposableFor(
        currentCurrency: String,
        formatCurrency: String,
        currencyDataSet: MutableLiveData<List<Pair<Date, Double>>>
    ) {
        registerDisposable(
            repository.getHistoryRates(
                currentCurrency,
                getStartDate(days),
                getDateFormatForApi(currentTimeMillis),
                formatCurrency
            )
                .subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread()
                )
                .subscribe({ response ->
                    currencyDataSet.postValue(formatResponseToDataSet(response, formatCurrency))
                }, {
                    showErrorConnectionEvent.postValue(it.localizedMessage)
                    DebugResponseLogger.log("getHistoryRatesFor", it.toString())
                })
        )

    }


    private fun formatResponseToDataSet(
        response: HistoryRatesResponse,
        currency: String
    ): List<Pair<Date, Double>> {
        return response.rates.map { Pair(it.key, it.value[currency] ?: 1.0) }
            .sortedWith(Comparator { rate: Pair<Date, Double>, rate2: Pair<Date, Double> -> // sort by date
                comparatorDates(
                    rate,
                    rate2
                )
            })
    }


}

private fun comparatorDates(
    rate: Pair<Date, Double>,
    rate2: Pair<Date, Double>
): Int {
    return when {
        rate.first.time > rate2.first.time -> 1
        rate.first.time < rate2.first.time -> -1
        else -> 0
    }
}