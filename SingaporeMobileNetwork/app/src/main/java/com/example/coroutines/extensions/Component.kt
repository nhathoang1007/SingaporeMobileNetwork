package com.example.coroutines.extensions

import androidx.appcompat.widget.AppCompatButton
import com.example.coroutines.R
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

fun AppCompatButton.isEnable(): Observer<Boolean> {
    return object : Observer<Boolean> {
        override fun onSubscribe(d: Disposable) {}

        override fun onNext(value: Boolean) {
            this@isEnable.apply {
                setBackgroundColor(if (value) {
                    R.color.purple_200
                } else {
                    R.color.black
                }.getColor())
            }
        }

        override fun onError(e: Throwable) {}

        override fun onComplete() {}
    }

}