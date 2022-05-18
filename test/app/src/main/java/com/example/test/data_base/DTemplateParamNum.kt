package com.example.test.data_base

import io.realm.Realm
import io.realm.RealmList

class DTemplateParamNum {
    val realm = Realm.getDefaultInstance()

    val mapSampleParamNum = mutableMapOf<String, ParamNum>(
        "Параметр" to ParamNum("Параметр", 0, true, 10, 0),
        "Навык" to ParamNum("Навык", 0, true, 10, 0),
    )

    fun initParamNumBySample(nameSample: String, nameParam: String): ParamNum {
        val paramSample = mapSampleParamNum.getValue(nameSample)
        realm.executeTransaction {
            paramSample.name = nameParam
        }
        return paramSample
    }

    val mapParamNum = mutableMapOf<String, ParamNum>(
        "Здоровье" to ParamNum("Здоровье", 0, false, 100, 0, "Базовые параметры"),
        "Тяжелое ранение" to ParamNum("Тяжелое ранение", 0, false, 50, 0, "Базовые параметры"),
        "Испытание против смерти" to ParamNum(
            "Испытание против смерти",
            0,
            false,
            10,
            0,
            "Базовые параметры"
        ),

        "Возраст" to ParamNum("Возраст", 0, false, null, 0, "Базовые параметры"),

        "Деньги, евробаксы" to ParamNum(
            "Деньги, евробаксы",
            0,
            false,
            null,
            0,
            "Базовые параметры"
        ),

        "Броня для головы" to ParamNum("Броня для головы", 0, false, null, 0),
        "Броня для тела" to ParamNum("Броня для тела", 0, false, null, 0),

        "Интеллект" to ParamNum("Интеллект", 0, false, 10, 0),
        "Воля" to ParamNum("Воля", 0, false, 10, 0),
        "Харизма" to ParamNum("Харизма", 0, false, 10, 0),
        "Эмпатия" to ParamNum("Эмпатия", 0, false, 10, 0),
        "Техника" to ParamNum("Техника", 0, false, 10, 0),
        "Реакция" to ParamNum("Реакция", 0, false, 10, 0),
        "Удача" to ParamNum("Удача", 0, false, 10, 0, ),
        "Телосложение" to ParamNum("Телосложение", 0, false, 10, 0, ),
        "Ловкость" to ParamNum("Ловкость", 0, false, 10, 0, ),
        "Скорость" to ParamNum("Скорость", 0, false, 10, 0, ),

        "Внимательность" to ParamNum("Внимательность", 0, true, 10, 0, ),
        "Выслеживание" to ParamNum("Выслеживание", 0, true, 10, 0, ),
        "Образование" to ParamNum("Образование", 0, true, 10, 0, ),
        "Знание местности" to ParamNum("Знание местности", 0, true, 10, 0, ),
        "Интерфейс" to ParamNum("Интерфейс", 0, true, 10, 0, ),
        "Стрельба" to ParamNum("Стрельба", 0, true, 10, 0, ),
        "Вождение" to ParamNum("Вождение", 0, true, 10, 0, ),
        "Уклонение" to ParamNum("Уклонение", 0, true, 10, 0, ),
        "Атлетика" to ParamNum("Атлетика", 0, true, 10, 0, ),
        "Скрытность" to ParamNum("Скрытность", 0, true, 10, 0, ),
        "Рукопашный бой" to ParamNum("Рукопашный бой", 0, true, 10, 0, ),
        "Оружие ближнего боя" to ParamNum("Оружие ближнего боя", 0, true, 10, 0, ),
        "Знание техники" to ParamNum("Знание техники", 0, true, 10, 0, ),
        "Кибертехника" to ParamNum("Кибертехника", 0, true, 10, 0, ),
        "Первая помощь" to ParamNum("Первая помощь", 0, true, 10, 0, ),
        "Подкуп" to ParamNum("Подкуп", 0, true, 10, 0, ),
        "Допрос" to ParamNum("Допрос", 0, true, 10, 0, ),
        "Убеждение" to ParamNum("Убеждение", 0, true, 10, 0, ),
        "Концентрация" to ParamNum("Концентрация", 0, true, 10, 0, ),
        "Общение" to ParamNum("Общение", 0, true, 10, 0, ),
        "Проницательность" to ParamNum("Проницательность", 0, true, 10, 0, ),
        "Игра на инструменте" to ParamNum("Игра на инструменте", 0, true, 10, 0, ),

        "Итого репутация" to ParamNum("Итого репутация", 0, false, null, 0, ),
    )

    val mapParamNumItem = mutableMapOf<String, ParamNum>(
        "Атака программы" to ParamNum("Атака программы", 0, false,  10, 0, ),
        "Защита программы" to ParamNum("Защита программы", 0, false,  10, 0, ),
        "Установка программы" to ParamNum("Установка программы", 0, false,  10, 0, ),
        "Внимательность программы" to ParamNum("Внимательность программы", 0,
            false,       10,         0 ),
        "Скорость программы" to ParamNum("Скорость программы", 0, false,  10, 0,),
    )

    fun initParamNum(param: ParamNum, value: Int): ParamNum {
        param.value = value
        return param
    }

    fun createListParamNum(map: Map<String, String>): RealmList<ParamNum> {
        val list = RealmList<ParamNum>()
        for ((key, value) in map) {
            if (mapParamNum[key]!=null){
            list.add(initParamNum(mapParamNum[key]!!, value.toInt()))}
        }
        return list
    }

}