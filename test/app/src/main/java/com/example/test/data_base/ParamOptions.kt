package com.example.test.data_base

import io.realm.RealmList
import io.realm.RealmObject

open class ParamOptions(
    var name: String = "",
    var value: String = "",
    var removable: Boolean = true, //   Можно ли его удалить
    var defMain: String = "",// например, Выберите пол персонажа
    var options: RealmList<String> = RealmList<String>(),
    var currentGroup: String = "",
    var forItemOrCharacter: Boolean = false, //  true - Item, false - Character
): RealmObject()

