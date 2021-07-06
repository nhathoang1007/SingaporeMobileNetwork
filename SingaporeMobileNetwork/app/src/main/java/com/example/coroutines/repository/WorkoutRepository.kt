package com.example.coroutines.repository

import com.example.coroutines.data.network.ApiGenerator
import com.example.coroutines.data.storage.realm.IWorkoutDao
import com.example.coroutines.model.Data
import com.example.coroutines.model.Record
import com.example.coroutines.model.Result
import io.reactivex.Observable

/**
 * Created by Nhat Vo on 14/06/2021.
 */
class WorkoutRepository constructor(
    private val service: ApiGenerator,
    private val dao: IWorkoutDao
) {

    fun loadWorkoutDataFromServer(): Observable<Result> {
        return service.createApi().getDataSource().map {
            it.result
        }
    }

    fun loadWorkoutFromLocalStorage(): Observable<MutableList<Data>> {
        return Observable.create {
            it.onError(Exception())
        }
    }

    fun saveWorkoutToLocalStorage(data: MutableList<Data>): Observable<Boolean> {
        return dao.saveData(data)
    }

    fun updateAssignmentMark(data: Data): Observable<Boolean> {
        return dao.mark(data)
    }
}