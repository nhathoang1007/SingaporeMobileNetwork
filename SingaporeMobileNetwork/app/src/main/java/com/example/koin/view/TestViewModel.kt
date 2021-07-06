package com.example.koin.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.koin.base.BaseViewModel
import com.example.koin.extensions.observeOnUiThread
import com.example.koin.model.Result
import com.example.koin.repository.IDataRepository
import com.example.koin.utils.observer.addTo

class TestViewModel(private val repository: IDataRepository) : BaseViewModel() {

    private val _dataObs = MutableLiveData<Result>()
    val dataObs: LiveData<Result>
        get() = _dataObs

    init {
        getData()
    }

    fun getData() {
        repository.loadDataFromLocalStorage()
            ?.observeOnUiThread()
            ?.doOnSubscribe { setLoading(true) }
            ?.onErrorReturn { Result() }
            ?.flatMap {
                _dataObs.postValue(it)
                repository.loadDataFromServer()
                    ?.observeOnUiThread()
            }?.flatMap { data ->
                _dataObs.postValue(data)
                repository.saveDataToLocalStorage(data)
                    ?.observeOnUiThread()
                    ?.onErrorReturn { true }
            }?.subscribe({
                setLoading(false)
            }, {
                setError(error = it)
            })?.addTo(compositeDisposableBag)
    }
}

