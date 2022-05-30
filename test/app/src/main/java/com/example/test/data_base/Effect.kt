package com.example.test.data_base

import io.realm.RealmObject

open class EffectAdd(
    var permanent:Boolean = true, // true - постоянный эффект, false - используемый эффект

    // if type add
    var property:String = "",// название характеристики, копируем из
    var impact:Int = 0,//
    var sign:Boolean = true, //true плюс, false - минус

    // заполняем до сюда, если это постоянный эффект
    var duration:Int? = null,// продолжительность воздействия после использования
    var rollback:Int? = null,// откат
):RealmObject()

open class EffectDamage(
    var name: String = "",
    var fightType: String? = "", // Ближний_бой    Дальний_бой   Автоматический_огонь   Взрывчатка, Нетраннинг против программ, Нетраннинг против людей, Нетраннинг против адских гончих,
    var damageType: String? = "",
    var numCount: Int = 0, // Колличество бросков кубика
    var dX: Int = 0, // d6 d4 d10 d12 и т.д. - это максимальное рандомное число урона от 1 до X
):RealmObject()
