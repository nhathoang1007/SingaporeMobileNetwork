package com.example.coroutines.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coroutines.base.BaseViewModel
import com.example.coroutines.extensions.observeOnUiThread
import com.example.coroutines.model.Result
import com.example.coroutines.repository.IDataRepository
import com.example.coroutines.utils.observer.addTo

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

