package com.example.test.data_base

import io.realm.RealmList

class TemplateGameSystem {
    val melle = "Ближний бой"
    val distant_battle = "Дальний бой"
    val automatic_fire = "Автоматический огонь"
    val explosive = "Взрывчатка"

    val paramItemCyberPuckSystem = RealmList<Item>(
        Item(
            "Киберрука",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Киберрука", melle, 1, 6)),
            group = "Оружие"
        ),
        Item(
            "Нож",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Нож", melle, 1, 6)),
            group = "Оружие"
        ),
        Item(
            "Большие костяшки",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(
                EffectWeapon(
                    "Большие костяшки",
                    melle,
                    2,
                    6
                )
            ),
            group = "Оружие"
        ),
        Item(
            "Пистолет среднего калибра",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(
                EffectWeapon(
                    "Пистолет среднего калибра",
                    distant_battle,
                    2,
                    6
                )
            ),
            group = "Оружие"
        ),
        Item(
            "Большие костяшки (пара)",
            "Усиленные остяшки пальцев наносят такие же тяжёлые удары,как костеты",
            true,
            effectsWeapon = RealmList<EffectWeapon>(
                EffectWeapon(
                    "Большие костяшки (пара)",
                    melle,
                    2,
                    6
                )
            ),
            group = "Киберимпланты"
        ),
        Item(
            "Киеррука (Потрошители)",
            "Киберрука, в которой спрятаны потрошители",
            true,
            effectsWeapon = RealmList<EffectWeapon>(
                EffectWeapon(
                    "Киберрука (Потрошители)",
                    melle,
                    2,
                    6
                )
            ),
            group = "Киберимпланты"
        ),
        Item(
            "Моноструна (одна)",
            "Прикрепленная к пальцу проволока из моноволокна режет любые органические материалы и пластик. Может служить удавкой, резиком или кнутом",
            true,
            effectsWeapon = RealmList<EffectWeapon>(
                EffectWeapon(
                    "Моноструна (одна)",
                    melle,
                    2,
                    6
                )
            ),
            group = "Киберимпланты"
        ),

        Item(
            "100 евробаксов немаркированными купюрами", "money", true,
            effectsAdd = RealmList<EffectAdd>(EffectAdd(false, "Деньги, евробаксы", 100, true)),
            group = "Снаряжение"
        ),
        Item(
            "Название поступка",
            "Описание поступка",
            true,
            effectsAdd = RealmList<EffectAdd>(
                EffectAdd(true, "Итого репутация", 1, true)
            ),
            group = "Репутация"
        ),
        Item(
            "Speedy Gonzalvez",
            "Increases your SPD by +4 as long as this program remains rezzed.",
            true,
            effectsAdd = RealmList(EffectAdd(true, "move", 4, true, null, null)),
            otherParamNum = RealmList<ParamNum>(
                DTemplateParamNum().initParamNum(
                    DTemplateParamNum().mapParamNumItem["Атака программы"]!!,
                    0
                ),
                DTemplateParamNum().initParamNum(
                    DTemplateParamNum().mapParamNumItem["Защита программы"]!!,
                    0
                ),
                DTemplateParamNum().initParamNum(
                    DTemplateParamNum().mapParamNumItem["Установка программы"]!!,
                    7
                )
            ),
            group = "Программы"
        ),
    )
    val paramNumCyberPuckSystem = RealmList<ParamNum>(
        ParamNum("Здоровье", 0, false, 100, 0, "Базовые параметры", false),
        ParamNum("Тяжелое ранение", 0, false, 50, 0, "Базовые параметры", false),
        ParamNum(
            "Испытание против смерти",
            0,
            false,
            10,
            0,
            "Базовые параметры", false
        ),

        ParamNum("Возраст", 0, false, null, 0, "Базовые параметры", false),

        ParamNum(
            "Деньги, евробаксы",
            0,
            false,
            null,
            0,
            "Базовые параметры", false
        ),

        ParamNum("Броня для головы", 0, false, null, 0, "Броня", false),
        ParamNum("Броня для тела", 0, false, null, 0, "Броня", false),

        ParamNum("Интеллект", 0, false, 10, 0, "Параметры", false),
        ParamNum("Воля", 0, false, 10, 0, "Параметры", false),
        ParamNum("Эмпатия", 0, false, 10, 0, "Параметры", false),
        ParamNum("Харизма", 0, false, 10, 0, "Параметры", false),
        ParamNum("Техника", 0, false, 10, 0, "Параметры", false),
        ParamNum("Реакция", 0, false, 10, 0, "Параметры", false),
        ParamNum("Удача", 0, false, 10, 0, "Параметры", false),
        ParamNum("Телосложение", 0, false, 10, 0, "Параметры", false),
        ParamNum("Ловкость", 0, false, 10, 0, "Параметры", false),
        ParamNum("Скорость", 0, false, 10, 0, "Параметры", false),

        ParamNum("Внимательность", 0, true, 10, 0, "Навыки", false),
        ParamNum("Выслеживание", 0, true, 10, 0, "Навыки", false),
        ParamNum("Образование", 0, true, 10, 0, "Навыки", false),
        ParamNum("Знание местности", 0, true, 10, 0, "Навыки", false),
        ParamNum("Интерфейс", 0, true, 10, 0, "Навыки", false),
        ParamNum("Стрельба", 0, true, 10, 0, "Навыки", false),
        ParamNum("Вождение", 0, true, 10, 0, "Навыки", false),
        ParamNum("Уклонение", 0, true, 10, 0, "Навыки", false),
        ParamNum("Атлетика", 0, true, 10, 0, "Навыки", false),
        ParamNum("Скрытность", 0, true, 10, 0, "Навыки", false),
        ParamNum("Рукопашный бой", 0, true, 10, 0, "Навыки", false),
        ParamNum("Оружие ближнего боя", 0, true, 10, 0, "Навыки", false),
        ParamNum("Знание техники", 0, true, 10, 0, "Навыки", false),
        ParamNum("Кибертехника", 0, true, 10, 0, "Навыки", false),
        ParamNum("Первая помощь", 0, true, 10, 0, "Навыки", false),
        ParamNum("Подкуп", 0, true, 10, 0, "Навыки", false),
        ParamNum("Допрос", 0, true, 10, 0, "Навыки", false),
        ParamNum("Убеждение", 0, true, 10, 0, "Навыки", false),
        ParamNum("Концентрация", 0, true, 10, 0, "Навыки", false),
        ParamNum("Общение", 0, true, 10, 0, "Навыки", false),
        ParamNum("Проницательность", 0, true, 10, 0, "Навыки", false),
        ParamNum("Игра на инструменте", 0, true, 10, 0, "Навыки", false),

        ParamNum("Итого репутация", 0, false, null, 0, "Репутация", false),
    )

    val paramOptionsCyberPuckSystem = RealmList<ParamOptions>(
        ParamOptions(
            "Тип оружия",
            "",
            false,
            "Выберите тип оружия",
            RealmList<String>("Ближний бой", "Дальний бой", "Автоматический огонь", "Взрывчатка"),
            "",
            true
        ),
        ParamOptions(
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
        ParamOptions(
            "Значимость",
            "",
            false,
            "Выберите значимость",
            RealmList<String>("Главный герой", "Второстепенный герой"),
            "Базовые параметры", false
        ),
        ParamOptions(
            "Роль",
            "",
            false,
            "Выберите роль",
            RealmList<String>("Рокер", "Соло", "Нетраннер", "Кочевник", "Техник", "Фиксер"),
            "Базовые параметры",
            false
        ),
        ParamOptions(
            "Пол",
            "",
            false,
            "Выберите пол",
            RealmList<String>("Мужской", "Женский"),
            "Базовые параметры",
            false
        ),
    )
    val paramStrCyberPuckSystem = RealmList<ParamStr>(
        ParamStr("Имя персонажа", "", removable = false, "Базовые параметры", false),
        ParamStr("Название игры", "", removable = false),
        ParamStr("Семья", "", removable = false, "Биография", false),
        ParamStr("Мотивация", "", removable = false, "Биография", false),
        ParamStr("Особенность", "", removable = true, "", true),
    )

    val cyberPuckSystem = GameSystem(
        1,
        "TemplateGameSystem",
        RealmList(
            "Базовые параметры",
            "Параметры",
            "Навыки",
            "Броня",
            "Оружие",
            "Киберимпланты",
            "Снаряжение",
            "Программы",
            "Репутация",
            "Биография",
        ),
        RealmList(RealmPair("Оружие", RealmList("Дальний бой", "Ближний бой"))),

        RealmList(
            RealmSomething(
                RealmList(
                    "Базовые параметры"
                )
            ),
            RealmSomething(
                RealmList(
                    "Параметры",
                    "Навыки"
                )
            ),
            RealmSomething(
                RealmList(
                    "Броня",
                    "Оружие"
                )
            ),
            RealmSomething(
                RealmList(
                    "Киберимпланты",
                    "Снаряжение",
                    "Программы"
                )
            ),
            RealmSomething(
                RealmList(
                    "Репутация",
                    "Биография"
                )
            ),
        ),
        RealmList<Character>(
            Character(
                0, 0,
                attributes = initGroupsParam(
                    mapOf(
                        "Базовые параметры" to mapOf(//название группы
                            "listParamStr" to mapOf(//тип листа
                                "Имя персонажа" to "Гриз"//название параметра и значение
                            ),
                            "listParamNum" to mapOf(
                                "Возраст" to "30",
                                "Здоровье" to "25",
                                "Тяжелое ранение" to "13",
                                "Испытание против смерти" to "5",
                                "Деньги, евробаксы" to "0",
                            ),
                            "listParamOptions" to mapOf(
                                "Значимость" to "Главный герой",
                                "Роль" to "Фиксер",
                                "Пол" to "Мужской",
                            )
                        ),
                        "Броня" to mapOf(
                            "listParamNum" to mapOf(
                                "Броня для головы" to "11",
                                "Броня для тела" to "11",
                            ),
                        ),
                        "Оружие" to mapOf(
                            "listItem" to mapOf(
                                "mapWeapon" to "Пистолет крупного калибра",
                                "mapWeapon" to "Пистолет-пулемёт среднего калибра",
                            ),
                        ),
                        "Параметры" to mapOf(
                            "listParamNum" to mapOf(
                                "Интеллект" to "7",
                            ),
                        ),
                        "Навыки" to mapOf(
                            "listParamNum" to mapOf(
                                "Атлетика" to "4",
                            ),
                        ),
                        "Киберимпланты" to mapOf(
                            "listItem" to mapOf(
                                "mapCyberware" to "Кибероптика (прицел)",
                            ),
                        ),
                        "Снаряжение" to mapOf(
                            "listItem" to mapOf(
                                "mapItem" to "100 евробаксов немаркированными купюрами",
                            ),
                        ),
                        "Программы" to mapOf(
                            "listItem" to mapOf(
                            ),
                        ),
                        "Репутация" to mapOf(
                            "listItem" to mapOf(
                                "mapItem" to "Поступок",
                            ),
                            "listParamNum" to mapOf(
                                "Итого репутация" to "0",
                            ),
                        ),
                        "Биография" to mapOf(
                            "listParamStr" to mapOf(
                                "Семья" to "",
                                "Мотивация" to "",
                            ),
                        ),
                    ),
                    paramStrCyberPuckSystem,
                    paramNumCyberPuckSystem,
                    paramOptionsCyberPuckSystem,
                    paramItemCyberPuckSystem
                )
            )
        ),
        paramItemCyberPuckSystem,
        paramNumCyberPuckSystem,
        paramStrCyberPuckSystem,
        paramOptionsCyberPuckSystem,
    )

    val DnDSystem = GameSystem(
        2,
        "DnDSystem",
        RealmList<String>(
            "Базовые параметры", "Параметры", "Навыки", "Биография", "Особенности персонажа",
            "Информация о игроке", "Снаряжение", "Заклинания", "Экипированное"
        ),
        RealmList<RealmPair>(
            // Оружие, Броня, Артефакты, Безделушки, Зелья
        ),
        // где первая строка - название группы, вторая название типа
        RealmList<RealmSomething>(),
        // где число - номер вкладки, вторая строка - название группы, не более 5 названий, названия и иконка вкладки не изменяются, подстраивайтесь под них
        // 1 "Базовые параметры"
        // 2 "Параметры", "Навыки",
        // 3 "Экипированное"
        // 4 "Снаряжение", "Заклинания"
        // 5 "Особенности персонажа" "Информация о игроке" "Биография"
        RealmList<Character>(),
        RealmList<Item>(),
        RealmList<ParamNum>(),
        RealmList<ParamStr>(),
        RealmList<ParamOptions>(),
    )


    fun initGroupsParam(
        map: Map<String, Map<String, Map<String, String>>>,
        listStr: RealmList<ParamStr>,
        listNum: RealmList<ParamNum>,
        listOptions: RealmList<ParamOptions>,
        listItem: RealmList<Item>,
    ): RealmList<GroupParam> {
        val list = RealmList<GroupParam>()
        for ((key, value) in map) {
            val gp = GroupParam()
            gp.title = key
            gp.attributes = initGroupAttributes(value, listStr, listNum, listOptions, listItem)
            list.add(gp)
        }
        return list
    }


    fun findParamStr(name: String, list: RealmList<ParamStr>): ParamStr {
        return list.singleOrNull {
            it.name == name
        } ?: ParamStr()
    }

    fun findParamNum(name: String, list: RealmList<ParamNum>): ParamNum {
        return list.singleOrNull {
            it.name == name
        } ?: ParamNum()
    }

    fun findParamOptions(name: String, list: RealmList<ParamOptions>): ParamOptions {
        return list.singleOrNull {
            it.name == name
        } ?: ParamOptions()
    }

    fun findItem(name: String, list: RealmList<Item>): Item {
        return list.singleOrNull {
            it.name == name
        } ?: Item()
    }

    fun setValueForParamStr(param: ParamStr, value: String): ParamStr {
        param.value = value
        return param
    }

    fun setValueForParamNum(param: ParamNum, value: String): ParamNum {
        param.value = value.toInt()
        return param
    }

    fun setValueForParamOptions(param: ParamOptions, value: String): ParamOptions {
        param.value = value
        return param
    }

    fun setValueListStr(
        map: Map<String, String>,
        templateList: RealmList<ParamStr>
    ): RealmList<ParamStr> {
        val list = RealmList<ParamStr>()
        for ((key, value) in map) {
            if (findParamStr(key, templateList) != ParamStr()) {
                list.add(setValueForParamStr(findParamStr(key, templateList), value))
            }
        }
        return list
    }

    fun createListItem(map: Map<String, String>, templateList: RealmList<Item>): RealmList<Item> {
        val list = RealmList<Item>()
        for ((key, value) in map) {
            findItem(value, templateList)
        }
        return list
    }

    fun setValueListNum(
        map: Map<String, String>,
        templateList: RealmList<ParamNum>
    ): RealmList<ParamNum> {
        val list = RealmList<ParamNum>()
        for ((key, value) in map) {
            if (findParamNum(key, templateList) != ParamNum()) {
                list.add(setValueForParamNum(findParamNum(key, templateList), value))
            }
        }
        return list
    }

    fun setValueListOptions(
        map: Map<String, String>,
        templateList: RealmList<ParamOptions>
    ): RealmList<ParamOptions> {
        val list = RealmList<ParamOptions>()
        for ((key, value) in map) {
            if (findParamOptions(key, templateList) != ParamOptions()) {
                list.add(setValueForParamOptions(findParamOptions(key, templateList), value))
            }
        }
        return list
    }


    fun initGroupAttributes(
        map: Map<String, Map<String, String>>,
        listStr: RealmList<ParamStr>,
        listNum: RealmList<ParamNum>,
        listOptions: RealmList<ParamOptions>,
        listItem: RealmList<Item>,
    ): GroupAttributes {
        val ga = GroupAttributes()
        for ((key, value) in map) {
            when (key) {
                "listParamStr" -> {
                    ga.listParamStr = setValueListStr(value, listStr)
                }
                "listParamNum" -> {
                    ga.listParamNum = setValueListNum(value, listNum)
                }
                "listParamOptions" -> {
                    ga.listParamOptions = setValueListOptions(value, listOptions)
                }
                "listItem" -> {
                    ga.listItem = createListItem(value, listItem)
                }
            }

        }
        return ga
    }
}

// Расчет инициативы
// + Ловкость
// Особенности рас , класс, черты, снаряженние
// кубик д20