package com.example.coroutines.data.storage.realm

import com.example.coroutines.model.Data
import io.reactivex.Observable

/**
 * Created by NhiNguyen on 4/16/2020.
 */

interface IWorkoutDao {
    fun saveData(data: MutableList<Data>): Observable<Boolean>
    fun mark(data: Data): Observable<Boolean>
    fun getAll(): Observable<MutableList<Data>>
}