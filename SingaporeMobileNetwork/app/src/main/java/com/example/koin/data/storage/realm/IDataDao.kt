package com.example.koin.data.storage.realm

import com.example.koin.model.Record
import io.reactivex.Observable

/**
 * Created by NhiNguyen on 4/16/2020.
 */

interface IDataDao {
    fun saveData(data: MutableList<Record>): Observable<Boolean>
    fun getAll(): Observable<MutableList<Record>>
}