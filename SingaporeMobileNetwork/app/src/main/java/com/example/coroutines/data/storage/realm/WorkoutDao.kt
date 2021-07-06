package com.example.coroutines.data.storage.realm

import com.example.coroutines.extensions.getDefault
import com.example.coroutines.model.Assignment
import com.example.coroutines.model.Data
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmList

/**
 * Created by NhiNguyen on 4/16/2020.
 */

class WorkoutDao : IWorkoutDao {
    override fun saveData(data: MutableList<Data>): Observable<Boolean> {
        return Observable.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    realm.insertOrUpdate(data)
                    emitter.onNext(true)
                    emitter.onComplete()
                }
            }
        }
    }

    override fun getAll(): Observable<MutableList<Data>> {
        return Observable.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val realmResults = realm.where(Data::class.java).findAll()
                    emitter.onNext(realm.copyFromRealm(realmResults))
                    emitter.onComplete()

                }
            }
        }
    }

    override fun mark(data: Data): Observable<Boolean> {
        return Observable.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val item = realm.where(Data::class.java)
                        .equalTo(ID, data._id).findFirst()
                    item?.apply {
                        day = data.day.getDefault()
                        timestamp = data.timestamp?.getDefault()
                        assignments = toRealmList(realm, data.assignments)
                    }
                    emitter.onNext(true)
                    emitter.onComplete()
                }
            }
        }
    }

    private fun toRealmList(realm: Realm, arrayList: RealmList<Assignment>): RealmList<Assignment> {
        val mRealmList = RealmList<Assignment>()
        arrayList.forEach {
            // Create a IncidentPhoto object which is managed by Realm.
            val data: Assignment = realm.createObject(Assignment::class.java)
            data._id = it._id
            data.isCompletedMarked = it.isCompletedMarked
            data.title = it.title
            data.status = it.status
            data.exerciseCount = it.exerciseCount
            mRealmList.add(data)
        }
        return mRealmList
    }

    companion object {
        private const val ID = "_id"
    }
}