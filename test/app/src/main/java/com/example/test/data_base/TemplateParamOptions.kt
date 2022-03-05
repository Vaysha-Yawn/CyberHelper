package com.example.test.data_base

import io.realm.RealmList

class TemplateParamOptions() {
    val mapParamOptions = mapOf<String, ParamOptions>(
        "Пол" to ParamOptions(
            "Пол",
            "",
            false,
            "Выберите пол",
            RealmList<String>("Мужской", "Женский"),
        ),
        "Роль" to ParamOptions(
            "Роль",
            "",
            false,
            "Выберите роль",
            RealmList<String>("Рокер", "Соло", "Нетраннер", "Кочевник", "Техник", "Фиксер"),
        ),
        "Значимость" to ParamOptions(
            "Значимость",
            "",
            false,
            "Выберите значимость",
            RealmList<String>("Главный герой", "Второстепенный герой"),
        ),
    )

    val mapParamOptionsItem = mapOf<String, ParamOptions>(
        "Тип программы" to ParamOptions(
            "Тип программы",
            "",
            true,
            "Выберите тип программы",
            RealmList<String>("Усиление", "Нападение", "Защита", "Противонетраннерский", "Противопрограммный")
        ),
    )

    val mapParamOptionsSupporting = mapOf<String, ParamOptions>(
        "Тип оружия" to ParamOptions(
            "Тип оружия",
            "",
            false,
            "Выберите тип оружия",
            RealmList<String>("Ближний бой", "Дальний бой", "Автоматический огонь", "Взрывчатка"),
        ),
        "Параметры для влияния" to ParamOptions(
            "Параметры для влияния",
            "",
            false,
            "Выберите параметр",
            createListAllNamParamNum(), // надо добавить сюда реально используемые значения, все, которые возможны,
        ),
        "Способность" to ParamOptions(
            "Способность",
            "",
            false,
            "Выберите способность",
            RealmList<String>("Детектор", "Лазейка", "Следопыт", "Побег", "Шокер", "Дешифровка", "Управление", "Вирус", "Маскировка"),
        ),
        "Проверка по навыку" to ParamOptions(
            "Проверка по навыку",
            "",
            false,
            "Выберите навык",
            RealmList<String>("Внимательность", "Выслеживание", "Образование", "Знание местности", "Интерфейс", "Стрельба", "Вождение", "Уклонение", "Атлетика",
                "Скрытность",),
        ),
    )

    fun createListAllNamParamNum(): RealmList<String> {
        val list = RealmList<String>()
        for (key in TemplateParamNum().mapParamNum.keys) {
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
            if (mapParamOptions[key]!=null){
            list.add(initParamOptions(mapParamOptions[key]!!, value))}
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