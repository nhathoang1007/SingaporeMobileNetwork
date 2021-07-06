package com.example.koin.repository

import com.example.koin.model.Result
import io.reactivex.Observable

interface IDataRepository {
    fun loadDataFromServer(): Observable<Result>

    fun loadDataFromLocalStorage(): Observable<Result>

    fun saveDataToLocalStorage(data: Result): Observable<Boolean>
}