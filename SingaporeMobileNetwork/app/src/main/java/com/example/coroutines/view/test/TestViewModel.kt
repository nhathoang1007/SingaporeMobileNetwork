package com.example.coroutines.view.test

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coroutines.extensions.observeOnUiThread
import com.example.coroutines.model.Data
import com.example.coroutines.model.Result
import com.example.coroutines.repository.WorkoutRepository
import com.example.coroutines.utils.Constants
import com.example.coroutines.utils.observer.addTo
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

class TestViewModel(private val repository: WorkoutRepository) : com.example.coroutines.base.BaseViewModel() {

    private val _dataObs = MutableLiveData<Result>()
    val dataObs: LiveData<Result>
        get() = _dataObs

    init {
        getData()
    }

    /**
     * Get start/end date by current day
     */
    @SuppressLint("SimpleDateFormat")
    private fun getDays(): Observable<MutableList<Data>> {
        return Observable.create { emitter ->
            val list = mutableListOf<Data>()
            val calendar = Calendar.getInstance()
            val monthBeginningCell = calendar[Calendar.DAY_OF_WEEK] - 2

            // move calendar backwards to the beginning of the week
            calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)
            val format = SimpleDateFormat(Constants.PATTERN_PARSE_TIME_SERVER)
            for (i in 0..6) {
                list.add(Data().apply {
                    timestamp = format.format(calendar.time)
                    day = i
                })
                calendar.add(Calendar.DATE, 1)
            }
            emitter.onNext(list)
            emitter.onComplete()
        }
    }

    private fun getData() {
        repository.loadWorkoutDataFromServer().observeOnUiThread()
            .doOnSubscribe { setLoading(true) }
            /*.flatMap {
                _dataObs.postValue(it)
                repository.loadWorkoutFromLocalStorage()
                    .observeOnUiThread()
                    .onErrorReturn { mutableListOf() }
                    .map { local -> local.merge() }
            }.flatMap {
                _dataObs.postValue(it)
                repository.loadWorkoutDataFromServer()
                    .observeOnUiThread()
                    .map { response -> response.merge() }
            }.flatMap { list ->
                _dataObs.postValue(list)
                repository.saveWorkoutToLocalStorage(list)
                    .observeOnUiThread()
            }*/.subscribe({
                _dataObs.value = it
                setLoading(false)
            }, {
                setError(error = it)
            }).addTo(compositeDisposableBag)
    }

//    private fun List<Data>.merge(): MutableList<Data> {
//        val value = _dataObs.value ?: mutableListOf()
//        val group = this.associateBy { it.day }
//
//        value.forEach {
//            group[it.day]?.apply {
//                it._id = _id
//                if (it.assignments.isNullOrEmpty()) {
//                    it.assignments = assignments
//                }
//            }
//        }
//        return value
//    }

    fun onMarkAssignment(data: Data) {
        repository.updateAssignmentMark(data)
            .observeOnUiThread()
            .subscribe({}, {setError(error = it) }).addTo(compositeDisposableBag)
    }
}

