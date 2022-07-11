package com.example.test.data_base.data_sample.test_data

import com.example.test.data_base.realm.other_realm_object.ParamNum
import io.realm.Realm
import io.realm.RealmList

class DTemplateParamNum {
    val realm = Realm.getDefaultInstance()

    val mapSampleParamNum = mutableMapOf<String, ParamNum>(
        "Параметр" to ParamNum(
            name = "Параметр",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0
        ),
        "Навык" to ParamNum(
            name = "Навык",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0
        ),
    )

    fun initParamNumBySample(nameSample: String, nameParam: String): ParamNum {
        val paramSample = mapSampleParamNum.getValue(nameSample)
        realm.executeTransaction {
            paramSample.name = nameParam
        }
        return paramSample
    }

    val mapParamNum = mutableMapOf<String, ParamNum>(
        "Здоровье" to ParamNum(
            name = "Здоровье",
            value = 0,
            removable = false,
            maxValue = 100,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Тяжелое ранение" to ParamNum(
            name = "Тяжелое ранение",
            value = 0,
            removable = false,
            maxValue = 50,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Испытание против смерти" to ParamNum(
            name = "Испытание против смерти",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),

        "Возраст" to ParamNum(
            name = "Возраст",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            forItemOrCharacter = false
        ),

        "Деньги, евробаксы" to ParamNum(
            name = "Деньги, евробаксы",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            forItemOrCharacter = false
        ),

        "Броня для головы" to ParamNum(
            name = "Броня для головы",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Броня для тела" to ParamNum(
            name = "Броня для тела",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            forItemOrCharacter = false
        ),

        "Интеллект" to ParamNum(
            name = "Интеллект",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Воля" to ParamNum(
            name = "Воля",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Харизма" to ParamNum(
            name = "Харизма",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Эмпатия" to ParamNum(
            name = "Эмпатия",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Техника" to ParamNum(
            name = "Техника",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Реакция" to ParamNum(
            name = "Реакция",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Удача" to ParamNum(
            name = "Удача",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Телосложение" to ParamNum(
            name = "Телосложение",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Ловкость" to ParamNum(
            name = "Ловкость",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Скорость" to ParamNum(
            name = "Скорость",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),

        "Внимательность" to ParamNum(
            name = "Внимательность",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Выслеживание" to ParamNum(
            name = "Выслеживание",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Образование" to ParamNum(
            name = "Образование",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Знание местности" to ParamNum(
            name = "Знание местности",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Интерфейс" to ParamNum(
            name = "Интерфейс",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Стрельба" to ParamNum(
            name = "Стрельба",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Вождение" to ParamNum(
            name = "Вождение",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Уклонение" to ParamNum(
            name = "Уклонение",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Атлетика" to ParamNum(
            name = "Атлетика",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Скрытность" to ParamNum(
            name = "Скрытность",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Рукопашный бой" to ParamNum(
            name = "Рукопашный бой",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Оружие ближнего боя" to ParamNum(
            name = "Оружие ближнего боя",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Знание техники" to ParamNum(
            name = "Знание техники",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Кибертехника" to ParamNum(
            name = "Кибертехника",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Первая помощь" to ParamNum(
            name = "Первая помощь",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Подкуп" to ParamNum(
            name = "Подкуп",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Допрос" to ParamNum(
            name = "Допрос",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Убеждение" to ParamNum(
            name = "Убеждение",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Концентрация" to ParamNum(
            name = "Концентрация",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Общение" to ParamNum(
            name = "Общение",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Проницательность" to ParamNum(
            name = "Проницательность",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),
        "Игра на инструменте" to ParamNum(
            name = "Игра на инструменте",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = false
        ),

        "Итого репутация" to ParamNum(
            name = "Итого репутация",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            forItemOrCharacter = false
        ),
    )

    val mapParamNumItem = mutableMapOf<String, ParamNum>(
        "Атака программы" to ParamNum(
            name = "Атака программы",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = true
        ),
        "Защита программы" to ParamNum(
            name = "Защита программы",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = true
        ),
        "Установка программы" to ParamNum(
            name = "Установка программы",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = true
        ),
        "Внимательность программы" to ParamNum(
            name = "Внимательность программы", value = 0,
            removable = false, maxValue = 10, minValue = 0, forItemOrCharacter = true
        ),
        "Скорость программы" to ParamNum(
            name = "Скорость программы",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            forItemOrCharacter = true
        ),
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