package com.example.test.data_base

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Game(
    @PrimaryKey
    var id: Int = 0,
    var name: String? = null
): RealmObject()