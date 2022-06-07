package com.example.test.test_data

import com.example.test.data_base.Character
import com.example.test.data_base.ParamOptions
import io.realm.RealmList

class DTemplateParamOptions {
    val mapParamOptions = mapOf<String, ParamOptions>(
        "Пол" to ParamOptions(
            "Пол",
            "",
            false,
            "Выберите пол",
            RealmList<String>("Мужской", "Женский"),
            "Базовые параметры",
            false
        ),
        "Роль" to ParamOptions(
            "Роль",
            "",
            false,
            "Выберите роль",
            RealmList<String>("Рокер", "Соло", "Нетраннер", "Кочевник", "Техник", "Фиксер"),
            "Базовые параметры",
            false
        ),
        "Значимость" to ParamOptions(
            "Значимость",
            "",
            false,
            "Выберите значимость",
            RealmList<String>("Главный герой", "Второстепенный герой"),
            "Базовые параметры", false
        ),
    )

    val mapParamOptionsItem = mapOf<String, ParamOptions>(
        "Тип программы" to ParamOptions(
            "Тип программы",
            "",
            true,
            "Выберите тип программы",
            RealmList<String>(
                "Усиление",
                "Нападение",
                "Защита",
                "Противонетраннерский",
                "Противопрограммный"
            ), "", true
        ),
    )

    val mapParamOptionsSupporting = mapOf<String, ParamOptions>(
        "Тип оружия" to ParamOptions(
            "Тип оружия",
            "",
            false,
            "Выберите тип оружия",
            RealmList<String>("Ближний бой", "Дальний бой", "Автоматический огонь", "Взрывчатка"),
            "",
            true
        ),
        "Способность" to ParamOptions(
            "Способность",
            "",
            false,
            "Выберите способность",
            RealmList<String>(
                "Детектор",
                "Лазейка",
                "Следопыт",
                "Побег",
                "Шокер",
                "Дешифровка",
                "Управление",
                "Вирус",
                "Маскировка"
            ),
            "", true
        ),
        "Проверка по навыку" to ParamOptions(
            "Проверка по навыку",
            "",
            false,
            "Выберите навык",
            RealmList<String>(
                "Внимательность",
                "Выслеживание",
                "Образование",
                "Знание местности",
                "Интерфейс",
                "Стрельба",
                "Вождение",
                "Уклонение",
                "Атлетика",
                "Скрытность",
            ),
            "", true
        ),
    )

    fun createListAllNamParamNum(): RealmList<String> {
        val list = RealmList<String>()
        for (key in DTemplateParamNum().mapParamNum.keys) {
            list.add(key)
        }
        return list
    }

    fun initParamOptions(param: ParamOptions, value: String): ParamOptions {
        param.value = value
        return param
    }

    fun createListParamOptions(map: Map<String, String>): RealmList<ParamOptions> {
        val list = RealmList<ParamOptions>()
        for ((key, value) in map) {
            if (mapParamOptions[key] != null) {
                list.add(initParamOptions(mapParamOptions[key]!!, value))
            }
        }
        return list
    }

    fun readParamOptions(character: Character, titleGroup: String, nameParam: String): String {
        var value = ""
        character.attributes.forEach { gp ->
            if (gp.title == titleGroup) {
                gp.attributes!!.listParamOptions.forEach { ps ->
                    if (ps.name == nameParam) {
                        value = ps.value
                    }
                }
            }
        }
        return value
    }
}

/*
"Параметры для влияния" to ParamOptions(
"Параметры для влияния",
"",
false,
"Выберите параметр",
createListAllNamParamNum(), // надо добавить сюда реально используемые значения, все, которые возможны,
"", true
),*/
