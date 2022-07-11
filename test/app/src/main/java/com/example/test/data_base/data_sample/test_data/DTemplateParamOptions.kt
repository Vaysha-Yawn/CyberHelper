package com.example.test.data_base.data_sample.test_data

import com.example.test.data_base.realm.character.Character
import com.example.test.data_base.realm.other_realm_object.ParamOptions
import io.realm.RealmList

class DTemplateParamOptions {
    val mapParamOptions = mapOf<String, ParamOptions>(
        "Пол" to ParamOptions(
            name = "Пол",
            value = "",
            removable = false,
            defMain = "Выберите пол",
            options = RealmList<String>("Мужской", "Женский"),
            forItemOrCharacter = false
        ),
        "Роль" to ParamOptions(
            name = "Роль",
            value = "",
            removable = false,
            defMain = "Выберите роль",
            options = RealmList<String>("Рокер", "Соло", "Нетраннер", "Кочевник", "Техник", "Фиксер"),
            forItemOrCharacter = false
        ),
        "Значимость" to ParamOptions(
            name = "Значимость",
            value = "",
            removable = false,
            defMain = "Выберите значимость",
            options = RealmList<String>("Главный герой", "Второстепенный герой"),
            forItemOrCharacter = false
        ),
    )

    val mapParamOptionsItem = mapOf<String, ParamOptions>(
        "Тип программы" to ParamOptions(
            name = "Тип программы",
            value = "",
            removable = true,
            defMain = "Выберите тип программы",
            options = RealmList<String>(
                "Усиление",
                "Нападение",
                "Защита",
                "Противонетраннерский",
                "Противопрограммный"
            ), forItemOrCharacter = true
        ),
    )

    val mapParamOptionsSupporting = mapOf<String, ParamOptions>(
        "Тип оружия" to ParamOptions(
            name = "Тип оружия",
            value = "",
            removable = false,
            defMain = "Выберите тип оружия",
            options = RealmList<String>("Ближний бой", "Дальний бой", "Автоматический огонь", "Взрывчатка"),
            forItemOrCharacter = true
        ),
        "Способность" to ParamOptions(
            name = "Способность",
            value = "",
            removable = false,
            defMain = "Выберите способность",
            options = RealmList<String>(
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
            forItemOrCharacter = true
        ),
        "Проверка по навыку" to ParamOptions(
            name = "Проверка по навыку",
            value = "",
            removable = false,
            defMain = "Выберите навык",
            options = RealmList<String>(
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
            forItemOrCharacter = true
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
                gp.attributes!!.listParamOptions!!.forEach { ps ->
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
