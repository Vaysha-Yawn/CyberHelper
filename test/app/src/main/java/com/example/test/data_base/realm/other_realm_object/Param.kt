package com.example.test.data_base.realm.other_realm_object

import io.realm.RealmObject

open class Param(
    open var name: String,
    open var removable: Boolean,
    open var forItemOrCharacter: Boolean
) : RealmObject() {

}