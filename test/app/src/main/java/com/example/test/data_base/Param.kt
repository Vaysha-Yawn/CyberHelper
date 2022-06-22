package com.example.test.data_base

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


// может сделать класс-родитель для параметров, это бы облегчило поиск и составление списков,
// потому что можно было бы просто найти параметр по имени, а потом понять какой он
open class Param(
    open var name: String = "",
    open var removable: Boolean = true, //   Можно ли его удалить
    open var forItemOrCharacter: Boolean = false, //  true - Item, false - Character
): RealmObject(){
    fun getCopy():Param{
        return realm.copyFromRealm(this)
    }
}

