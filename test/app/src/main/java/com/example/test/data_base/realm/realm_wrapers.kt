package com.example.test.data_base.realm

import io.realm.RealmList
import io.realm.RealmObject

open class RealmPair(
    var key: String = "",
    var value: RealmList<String> = RealmList(),
) : RealmObject()

open class RealmSomething(
    var key: RealmList<String> = RealmList()
) : RealmObject()
