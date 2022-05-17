package com.example.test.data_base

import io.realm.RealmList

class TemplateGameSystem {
    val melle = "Ближний бой"
    val distant_battle = "Дальний бой"
    val automatic_fire = "Автоматический огонь"
    val explosive = "Взрывчатка"

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

        RealmList<RealmPair<Int, RealmList<String>>>(
            RealmPair(
                1, RealmList(
                    "Базовые параметры"
                )
            ),
            RealmPair(
                2, RealmList(
                    "Параметры",
                    "Навыки"
                )
            ),
            RealmPair(
                3, RealmList(
                    "Броня",
                    "Оружие"
                )
            ),
            RealmPair(
                4, RealmList(
                    "Киберимпланты",
                    "Снаряжение",
                    "Программы"
                )
            ),
            RealmPair(
                5, RealmList(
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
                    )
                )
            )
        ),
        RealmList<Item>(
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
                    TemplateParamNum().initParamNum(
                        TemplateParamNum().mapParamNumItem["Атака программы"]!!,
                        0
                    ),
                    TemplateParamNum().initParamNum(
                        TemplateParamNum().mapParamNumItem["Защита программы"]!!,
                        0
                    ),
                    TemplateParamNum().initParamNum(
                        TemplateParamNum().mapParamNumItem["Установка программы"]!!,
                        7
                    )
                ),
                group = "Программы"
            ),
        ),
        RealmList<ParamNum>(),
        RealmList<ParamStr>(),
        RealmList<ParamOptions>(),
    )

    val DnDSystem = GameSystem(
        1,
        "DnDSystem",
        RealmList<String>("Базовые параметры","Параметры", "Навыки", "Биография", "Особенности персонажа",
        "Информация о игроке", "Снаряжение", "Заклинания", "Экипированное"),
        RealmList<RealmPair<String, RealmList<String>>>(
            // Оружие, Броня, Артефакты, Безделушки, Зелья
        ),
        // где первая строка - название группы, вторая название типа
        RealmList<RealmPair<Int, RealmList<String>>>(),
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


    fun initGroupsParam(map: Map<String, Map<String, Map<String, String>>>): RealmList<GroupParam> {
        val list = RealmList<GroupParam>()
        for ((key, value) in map) {
            val gp = GroupParam()
            gp.title = key
            gp.attributes = initGroupAttributes(value)
            list.add(gp)
        }
        return list
    }

    fun initGroupAttributes(
        map: Map<String, Map<String, String>>
    ): GroupAttributes {
        val ga = GroupAttributes()
        for ((key, value) in map) {
            when (key) {
                "listParamStr" -> {
                    ga.listParamStr = TemplateParamStr().createListParamStr(value)
                }
                "listParamNum" -> {
                    ga.listParamNum = TemplateParamNum().createListParamNum(value)
                }
                "listParamOptions" -> {
                    ga.listParamOptions = TemplateParamOptions().createListParamOptions(value)
                }
                "listItem" -> {
                    ga.listItem = TemplateItem().createListItem(value)
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