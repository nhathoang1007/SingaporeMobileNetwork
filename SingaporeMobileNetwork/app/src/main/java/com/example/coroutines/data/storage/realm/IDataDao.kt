package com.example.coroutines.data.storage.realm

import com.example.coroutines.model.Record
import io.reactivex.Observable

/**
 * Created by NhiNguyen on 4/16/2020.
 */

interface IDataDao {
    fun saveData(data: MutableList<Record>): Observable<Boolean>
    fun getAll(): Observable<MutableList<Record>>
}