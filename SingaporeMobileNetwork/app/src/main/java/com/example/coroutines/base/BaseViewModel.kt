package com.example.coroutines.base

import androidx.lifecycle.*
import com.example.coroutines.base.viewmodel.DataState
import com.example.coroutines.extensions.getDefault
import com.example.coroutines.extensions.logError
import com.example.coroutines.utils.observer.DisposableBag
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
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

    /**
     * Process Retrofit callback with auto show/hide loading indicator and error notification
     */
    abstract inner class FullCallbackWrapper<T> constructor(private val isLoading: Boolean) : DisposableObserver<T>() {
        protected abstract fun onResponse(response: T)
        override fun onStart() {
            setLoading(isLoading)
        }

        override fun onNext(t: T) {
            onResponse(t)
            setLoading(false)
        }

        override fun onComplete() {
            setLoading(false)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            setLoading(false)
            setError(error = e)
        }
    }
}