package com.example.coroutines.base

import androidx.lifecycle.*
import com.example.coroutines.base.viewmodel.DataState
import com.example.coroutines.extensions.getDefault
import com.example.coroutines.extensions.logError
import com.example.coroutines.utils.observer.DisposableBag
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    private val TAG = this::class.simpleName

    val data = BehaviorSubject.create<String>()

    protected val _stateObs = MutableLiveData<DataState>()
    val stateObs: LiveData<DataState>
        get() = _stateObs

    protected val compositeDisposableBag: DisposableBag by lazy {
        DisposableBag()
    }

    fun setLoading(isLoading: Boolean) {
        TAG?.logError("$isLoading")
        _stateObs.postValue(DataState.Loading(isLoading = isLoading))
    }

    fun setError(message: String? = null, error: Throwable? = null) {
        TAG?.logError(error?.message.getDefault())
        _stateObs.postValue(DataState.Failure(message = message, t = error))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposableBag.onStop()
    }
}