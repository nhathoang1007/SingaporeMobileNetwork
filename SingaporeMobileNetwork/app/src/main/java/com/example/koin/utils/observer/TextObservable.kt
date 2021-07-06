package com.example.koin.utils.observer

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

class TextObservable(private val view: TextView) : InitialValueObservable<String>() {
    override fun subscribeListener(observer: Observer<in String>?) {
        val listener = Listener(view, observer)
        observer!!.onSubscribe(listener)
        view.addTextChangedListener(listener)
    }

    override fun getInitialValue(): String {
        return view.text.toString()
    }

    internal class Listener(
        private val view: TextView,
        private val observer: Observer<in String>?
    ) :
        MainThreadDisposable(), TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (!isDisposed) {
                observer?.onNext(s.toString())
            }
        }

        override fun afterTextChanged(s: Editable) {}
        override fun onDispose() {
            view.removeTextChangedListener(this)
        }
    }
}