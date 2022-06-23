package com.example.test.data_base

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ParamOptions(
    @PrimaryKey
    var id:Int = 0,
    var name: String = "",
    var value: String = "",
    var removable: Boolean = true, //   Можно ли его удалить
    var defMain: String = "",// например, Выберите пол персонажа
    var options: RealmList<String> = RealmList<String>(),
    var forItemOrCharacter: Boolean = false, //  true - Item, false - Character
): RealmObject(){
    fun getCopy():ParamOptions{
        return realm.copyFromRealm(this)
    }
}

