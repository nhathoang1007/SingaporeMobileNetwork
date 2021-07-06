package com.example.koin.repository

import com.example.koin.data.network.ApiGenerator
import com.example.koin.data.storage.realm.IDataDao
import com.example.koin.extensions.observeOnUiThread
import com.example.koin.model.Result
import io.reactivex.Observable

/**
 * Created by Nhat Vo on 14/06/2021.
 */
class DataRepository constructor(
    private val service: ApiGenerator,
    private val dao: IDataDao
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