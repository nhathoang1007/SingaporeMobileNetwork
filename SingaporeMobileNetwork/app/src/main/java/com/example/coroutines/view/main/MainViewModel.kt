package com.example.coroutines.view.main

import android.util.Log
import com.example.coroutines.data.storage.SharedKey
import com.example.coroutines.extensions.bindTo
import com.example.coroutines.extensions.observeOnUiThread
import com.example.coroutines.utils.SharedPrefs
import com.example.coroutines.utils.observer.addTo
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainViewModel(private val prefs: SharedPrefs) : com.example.coroutines.base.BaseViewModel() {

    private val emailObs = PublishSubject.create<String>()
    private val passwordObs = PublishSubject.create<String>()

    val isEnableConfirm = PublishSubject.create<Boolean>()

    init {
        prefs.put(SharedKey.TOKEN, "Jason")

        Observable.combineLatest(emailObs, passwordObs) { email, password ->
            email.isNotEmpty() && password.isNotEmpty()
        }.bindTo(isEnableConfirm).addTo(compositeDisposableBag)
    }

    fun onEmailChanged(email: String) {
        emailObs.onNext(email)
    }

    fun onPasswordChanged(password: String) {
        passwordObs.onNext(password)
    }

    fun testPrefs() {
        prefs.get(SharedKey.TOKEN, String::class.java)
            .observeOnUiThread()
            .doOnSubscribe { setLoading(true) }
            .doFinally { setLoading(false) }
            .subscribe({
                Log.e("Test", it)
            }, {
                Log.e("Test", "Error")
            }).addTo(compositeDisposableBag)
    }
}

