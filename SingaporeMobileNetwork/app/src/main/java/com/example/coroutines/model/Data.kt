package com.example.coroutines.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Nhat Vo on 14/06/2021.
 */
open class Data(
    @PrimaryKey var _id: String = "",
    var day: Int = 0,
    var timestamp: String? = null,
    var assignments: RealmList<Assignment> = RealmList()
): RealmObject()