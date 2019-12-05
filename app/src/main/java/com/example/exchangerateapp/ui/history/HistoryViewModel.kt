package com.example.exchangerateapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exchangerateapp.data.RatesRepository
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
    private var _ronDataset = MutableLiveData<List<Pair<Date, Double>>>()
    val ronDataset: LiveData<List<Pair<Date, Double>>>
        get() = _ronDataset

    fun getHistoryRatesFor(currentCurrency: String) {
        registerDisposable(
            repository.getHistoryRates(
                currentCurrency,
                getStartDate(days),
                getDateFormatForApi(currentTimeMillis),
                currencyRON
            )
                .subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread()
                ).subscribe({ response ->
                    val dataset: List<Pair<Date, Double>> =
                        response.rates.map { Pair(it.key, it.value[currencyRON] ?: 1.0) }
                            .sortedWith(Comparator { rate: Pair<Date, Double>, rate2: Pair<Date, Double> ->
                                comparatorLong(
                                    rate,
                                    rate2
                                )
                            })

                    _ronDataset.postValue(dataset)

                },

                    {
                        showErrorConnectionEvent.call()
                        DebugResponseLogger.log("getHistoryRatesFor", it.toString())
                    }

                ))
    }


}

private fun comparatorLong(
    rate: Pair<Date, Double>,
    rate2: Pair<Date, Double>
): Int {
    return when {
        rate.first.time > rate2.first.time -> 1
        rate.first.time < rate2.first.time -> -1
        else -> 0
    }
}