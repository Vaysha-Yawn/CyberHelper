package com.example.test.data_base

import io.realm.RealmList
import io.realm.RealmObject

open class FightType(
    ////////// общее
    var name:String = "",
    var forAllOrItem:Boolean = true,// false for all true for item

    ////////// как расчитывается сложность
    var difficult:String = "",//
    var goal:Boolean = true, // можно ли выбрать целей, кто делает бросок или на кого нападают, лучше ставить этот параметр автоматически

    // параметры броска
    var critical:Boolean = true,
    var advantageAndDisadvantage:Boolean = true,// true - on, false - off ; возможность задавать преимущество или помеху
    var difRollByD:Int? = 10,// null если бросок не нужен для сложности, число - максимальная грьбика, прим 1Д10
    var difParameters:RealmList<String> = RealmList<String>(),
    var modificators:Boolean = true,// возможность ставить и учитывать модификаторы

    /////////// как производится бросок героя
    var roll: String = "",//

    //////////// что происходит в случае успешного прохождения проверки
    var successComment: String = "",// если нулловый, тогда нет
    var successEffectAdd: RealmList<EffectAdd> = RealmList<EffectAdd>(),// effectAttack
    var successDamageBoolean: Boolean = false,
    var defaultDamage : RealmList<Int> = RealmList<Int>(),// приммер listOf(3, 10) а пара чисел а и б это адб урона, например 1д10 урона
    // нужен при forAllOrItem = false

    ///////////// что происходит в случае провала
    var failComment: String = "",// есть чекбокс ложь, то этот параметр нулловый
    var failEffectAdd: RealmList<EffectAdd> = RealmList<EffectAdd>(),
):RealmObject()
// тогда необходимо для простоты доступа везде вместо названия типа аттаки использовать экземпляр этого класса