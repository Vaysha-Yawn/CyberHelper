package com.example.test.data_base

import io.realm.RealmList
import io.realm.RealmObject

open class ParamNum(
    var name: String = "",
    var value: Int = 0,
    var removable: Boolean = true, //   Можно ли его удалить
    var maxValue: Int? = null,
    var minValue: Int? = null,
) : RealmObject()

