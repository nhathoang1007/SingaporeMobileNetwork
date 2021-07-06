package com.example.coroutines.repository

import com.example.coroutines.data.network.ApiGenerator
import com.example.coroutines.data.storage.realm.IDataDao
import com.example.coroutines.extensions.observeOnUiThread
import com.example.coroutines.model.Result
import io.reactivex.Observable

/**
 * Created by Nhat Vo on 14/06/2021.
 */
class DataRepository constructor(
    var service: ApiGenerator,
    var dao: IDataDao
): IDataRepository {

    override fun loadDataFromServer(): Observable<Result> {
        return service.createApi().getDataSource().map {
            it.result.records?.sortBy { data -> data.id }
            it.result
        }
    }

    override fun loadDataFromLocalStorage(): Observable<Result> {
        return dao.getAll().observeOnUiThread().map {
            it.sortBy { data -> data.id }
            Result(records = it)
        }
    }

    override fun saveDataToLocalStorage(data: Result): Observable<Boolean> {
        return dao.saveData(data.records ?: mutableListOf())
    }
}