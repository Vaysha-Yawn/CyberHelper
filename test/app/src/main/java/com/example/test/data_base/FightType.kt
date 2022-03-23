package com.example.test.data_base

import io.realm.RealmList

data class FightType (
    var name:String = "",
    var forAllOrItem:Boolean = true,// false for all true for item
    var difficult:String = "",
    var roll: String = "",
    var successComment: String? = null,// если нулловый, тогда нет
    var successEffectAdd: RealmList<EffectAdd>? = null,// effectAttack
    var successDamageBoolean: Boolean = false,
    var defaultDamage : List<List<Int>>? = null,// приммер listOf(listOf(3, 10), listOf(1, 6))
    // где кол-во внутр листов - это кол-во атак, а пара чисел а и б это адб урона, например 1д10 урона
    // нужен при forAllOrItem = false
    var failComment: String? = null,// есть чекбокс ложь, то этот параметр нулловый
    var failEffectAdd: RealmList<EffectAdd>? = null,
)
// тогда необходимо для простоты доступа везде вместо названия типа аттаки использовать экземпляр этого класса