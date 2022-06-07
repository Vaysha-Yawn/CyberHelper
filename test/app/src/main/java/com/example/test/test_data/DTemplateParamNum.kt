package com.example.test.test_data

import com.example.test.data_base.ParamNum
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
        "Здоровье" to ParamNum("Здоровье", 0, false, 100, 0, "Базовые параметры", false),
        "Тяжелое ранение" to ParamNum(
            "Тяжелое ранение",
            0,
            false,
            50,
            0,
            "Базовые параметры",
            false
        ),
        "Испытание против смерти" to ParamNum(
            "Испытание против смерти",
            0,
            false,
            10,
            0,
            "Базовые параметры", false
        ),

        "Возраст" to ParamNum("Возраст", 0, false, null, 0, "Базовые параметры", false),

        "Деньги, евробаксы" to ParamNum(
            "Деньги, евробаксы",
            0,
            false,
            null,
            0,
            "Базовые параметры", false
        ),

        "Броня для головы" to ParamNum("Броня для головы", 0, false, null, 0, "Броня", false),
        "Броня для тела" to ParamNum("Броня для тела", 0, false, null, 0, "Броня", false),

        "Интеллект" to ParamNum("Интеллект", 0, false, 10, 0, "Параметры", false),
        "Воля" to ParamNum("Воля", 0, false, 10, 0, "Параметры", false),
        "Харизма" to ParamNum("Харизма", 0, false, 10, 0, "Параметры", false),
        "Эмпатия" to ParamNum("Эмпатия", 0, false, 10, 0, "Параметры", false),
        "Техника" to ParamNum("Техника", 0, false, 10, 0, "Параметры", false),
        "Реакция" to ParamNum("Реакция", 0, false, 10, 0, "Параметры", false),
        "Удача" to ParamNum("Удача", 0, false, 10, 0, "Параметры", false),
        "Телосложение" to ParamNum("Телосложение", 0, false, 10, 0, "Параметры", false),
        "Ловкость" to ParamNum("Ловкость", 0, false, 10, 0, "Параметры", false),
        "Скорость" to ParamNum("Скорость", 0, false, 10, 0, "Параметры", false),

        "Внимательность" to ParamNum("Внимательность", 0, true, 10, 0, "Навыки", false),
        "Выслеживание" to ParamNum("Выслеживание", 0, true, 10, 0, "Навыки", false),
        "Образование" to ParamNum("Образование", 0, true, 10, 0, "Навыки", false),
        "Знание местности" to ParamNum("Знание местности", 0, true, 10, 0, "Навыки", false),
        "Интерфейс" to ParamNum("Интерфейс", 0, true, 10, 0, "Навыки", false),
        "Стрельба" to ParamNum("Стрельба", 0, true, 10, 0, "Навыки", false),
        "Вождение" to ParamNum("Вождение", 0, true, 10, 0, "Навыки", false),
        "Уклонение" to ParamNum("Уклонение", 0, true, 10, 0, "Навыки", false),
        "Атлетика" to ParamNum("Атлетика", 0, true, 10, 0, "Навыки", false),
        "Скрытность" to ParamNum("Скрытность", 0, true, 10, 0, "Навыки", false),
        "Рукопашный бой" to ParamNum("Рукопашный бой", 0, true, 10, 0, "Навыки", false),
        "Оружие ближнего боя" to ParamNum("Оружие ближнего боя", 0, true, 10, 0, "Навыки", false),
        "Знание техники" to ParamNum("Знание техники", 0, true, 10, 0, "Навыки", false),
        "Кибертехника" to ParamNum("Кибертехника", 0, true, 10, 0, "Навыки", false),
        "Первая помощь" to ParamNum("Первая помощь", 0, true, 10, 0, "Навыки", false),
        "Подкуп" to ParamNum("Подкуп", 0, true, 10, 0, "Навыки", false),
        "Допрос" to ParamNum("Допрос", 0, true, 10, 0, "Навыки", false),
        "Убеждение" to ParamNum("Убеждение", 0, true, 10, 0, "Навыки", false),
        "Концентрация" to ParamNum("Концентрация", 0, true, 10, 0, "Навыки", false),
        "Общение" to ParamNum("Общение", 0, true, 10, 0, "Навыки", false),
        "Проницательность" to ParamNum("Проницательность", 0, true, 10, 0, "Навыки", false),
        "Игра на инструменте" to ParamNum("Игра на инструменте", 0, true, 10, 0, "Навыки", false),

        "Итого репутация" to ParamNum("Итого репутация", 0, false, null, 0, "Репутация", false),
    )

    val mapParamNumItem = mutableMapOf<String, ParamNum>(
        "Атака программы" to ParamNum("Атака программы", 0, false, 10, 0, "", true),
        "Защита программы" to ParamNum("Защита программы", 0, false, 10, 0, "", true),
        "Установка программы" to ParamNum("Установка программы", 0, false, 10, 0, "", true),
        "Внимательность программы" to ParamNum(
            "Внимательность программы", 0,
            false, 10, 0, "", true
        ),
        "Скорость программы" to ParamNum("Скорость программы", 0, false, 10, 0, "", true),
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