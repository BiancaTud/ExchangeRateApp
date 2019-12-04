package com.example.exchangerateapp.ui

import androidx.lifecycle.ViewModel
import com.example.exchangerateapp.ui.event.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent

open class BaseViewModel : ViewModel(), KoinComponent {
    private val compositeDisposable = CompositeDisposable()

    var showProgressEvent = SingleLiveEvent<Boolean>()
    var showErrorConnectionEvent = SingleLiveEvent<Boolean>()

    fun registerDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        clearDisposables()

    }

    private fun clearDisposables() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun unregisterDisposable(disposable: Disposable){
        compositeDisposable.remove(disposable)
    }
}