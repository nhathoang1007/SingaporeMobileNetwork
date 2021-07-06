package com.example.coroutines.utils.observer

import com.blankj.utilcode.util.LogUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableBag {
    var disposableBag: CompositeDisposable? = null

    init {
        disposableBag = CompositeDisposable()
    }

    fun add(disposable: Disposable) {
        if (disposableBag != null) {
            disposableBag?.add(disposable)
        } else {
            throw NotImplementedError("must bind AutoDisposable to a Lifecycle first")
        }
    }

    fun onStop() {
        disposableBag?.dispose()
        LogUtils.e("Cancel this task success----->")
    }
}

fun Disposable.addTo(disposableBag: DisposableBag) {
    disposableBag.add(this)
}