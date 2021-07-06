package com.example.koin.data.storage.realm

import com.example.koin.model.Record
import io.reactivex.Observable
import io.realm.Realm

/**
 * Created by NhiNguyen on 4/16/2020.
 */

class DataDao : IDataDao {
    override fun saveData(data: MutableList<Record>): Observable<Boolean> {
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

    override fun getAll(): Observable<MutableList<Record>> {
        return Observable.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val realmResults = realm.where(Record::class.java).findAll()
                    emitter.onNext(realm.copyFromRealm(realmResults))
                    emitter.onComplete()

                }
            }
        }
    }
}