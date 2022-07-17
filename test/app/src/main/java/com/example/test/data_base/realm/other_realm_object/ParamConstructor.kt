package com.example.test.data_base.realm.other_realm_object

class ParamConstructor() {
    class ParamSome(
        override var name: String,
        var value: Int,
        override var removable: Boolean,
        override var forItemOrCharacter: Boolean
    ) : Param(
        name,
        removable,
        forItemOrCharacter
    ) {
        fun getCopy(): ParamSome {
            return realm.copyFromRealm(this)
        }
    }

    /*fun makeParam(name: String,
                  removable: Boolean,
                  forItemOrCharacter: Boolean):Param {
        return Param(name, removable, forItemOrCharacter)
    }*/

    fun makeParamSome(
        name: String,
        value: Int = 0,
        removable: Boolean,
        forItemOrCharacter: Boolean
    ): ParamSome {
        return ParamSome(
            name,
            value,
            removable,
            forItemOrCharacter
        )
    }


}