package com.example.coroutines.repository

import com.example.coroutines.extensions.observeOnUiThread
import com.example.coroutines.model.Result
import io.reactivex.Observable

interface IDataRepository {
    fun loadDataFromServer(): Observable<Result>

    fun loadDataFromLocalStorage(): Observable<Result>

    fun saveDataToLocalStorage(data: Result): Observable<Boolean>
}