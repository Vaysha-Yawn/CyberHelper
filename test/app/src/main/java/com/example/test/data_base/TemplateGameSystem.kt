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
            effectsDamage = RealmList<EffectDamage>(EffectDamage("Киберрука", melle, 1, 6)),
            group = "Оружие"
        ),
        Item(
            "Нож",
            "",
            true,
            effectsDamage = RealmList<EffectDamage>(EffectDamage("Нож", melle, 1, 6)),
            group = "Оружие"
        ),
        Item(
            "Большие костяшки",
            "",
            true,
            effectsDamage = RealmList<EffectDamage>(
                EffectDamage(
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
            effectsDamage = RealmList<EffectDamage>(
                EffectDamage(
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
            effectsDamage = RealmList<EffectDamage>(
                EffectDamage(
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
            effectsDamage = RealmList<EffectDamage>(
                EffectDamage(
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
            effectsDamage = RealmList<EffectDamage>(
                EffectDamage(
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
        "cyberPuckSystem",
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

        tabsToGroup = RealmList(
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
        templateCharacter = RealmList<Character>(
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

        templateItem = paramItemCyberPuckSystem,
        templateParamNum = paramNumCyberPuckSystem,
        templateParamStr = paramStrCyberPuckSystem,
        templateParamOptions = paramOptionsCyberPuckSystem,
    )

    /////// дозаполнять
    val paramNumDnDSystem = RealmList<ParamNum>(
        ParamNum("Возраст", 0, false, null, 0, "Базовые параметры", false),
        ParamNum("Уровень", 0, false, null, 0, "Базовые параметры", false),
        ParamNum("Опыт", 0, false, null, 0, "Базовые параметры", false),

        ParamNum("Вдохновение", 0, false, null, 0, "Базовые параметры", false),
        ParamNum("Бонус мастерства", 0, false, null, 0, "Базовые параметры", false),

        ParamNum("Текущие хиты", 0, false, null, 0, "Базовые параметры", false),
        ParamNum("Временные хиты", 0, false, null, 0, "Базовые параметры", false),
        ParamNum("Максимальные хиты", 0, false, null, 0, "Базовые параметры", false),
        ParamNum("Бонус к инициативе", 0, false, null, 0, "Базовые параметры", false),
        ParamNum("Скорость", 0, false, null, 0, "Базовые параметры", false),

        ParamNum("Класс доспеха", 0, false, null, 0, "Базовые параметры", false),

        ParamNum("Сила", 0, false, null, 0, "Параметры", false),
        ParamNum("Ловкость", 0, false, null, 0, "Параметры", false),
        ParamNum("Телосложение", 0, false, null, 0, "Параметры", false),
        ParamNum("Интеллект", 0, false, null, 0, "Параметры", false),
        ParamNum("Мудрость", 0, false, null, 0, "Параметры", false),
        ParamNum("Харизма", 0, false, null, 0, "Параметры", false),

        ParamNum("Сила спасбросок", 0, false, null, 0, "Спасброски", false),
        ParamNum("Ловкость спасбросок", 0, false, null, 0, "Спасброски", false),
        ParamNum("Телосложение спасбросок", 0, false, null, 0, "Спасброски", false),
        ParamNum("Интеллект спасбросок", 0, false, null, 0, "Спасброски", false),
        ParamNum("Мудрость спасбросок", 0, false, null, 0, "Спасброски", false),
        ParamNum("Харизма спасбросок", 0, false, null, 0, "Спасброски", false),

        ParamNum("Акробатика", 0, false, 30, 0, "Навыки", false),
        ParamNum("Анализ", 0, false, 30, 0, "Навыки", false),
        ParamNum("Атлетика", 0, false, 30, 0, "Навыки", false),
        ParamNum("Внимательность", 0, false, 30, 0, "Навыки", false),
        ParamNum("Выживание", 0, false, 30, 0, "Навыки", false),
        ParamNum("Выступление", 0, false, 30, 0, "Навыки", false),
        ParamNum("Запугивание", 0, false, 30, 0, "Навыки", false),
        ParamNum("Истроия", 0, false, 30, 0, "Навыки", false),
        ParamNum("Ловкость рук", 0, false, 30, 0, "Навыки", false),
        ParamNum("Магия", 0, false, 30, 0, "Навыки", false),
        ParamNum("Проницательность", 0, false, 30, 0, "Навыки", false),
        ParamNum("Религия", 0, false, 30, 0, "Навыки", false),
        ParamNum("Скрытность", 0, false, 30, 0, "Навыки", false),
        ParamNum("Убеждение", 0, false, 30, 0, "Навыки", false),
        ParamNum("Уход за животными", 0, false, 30, 0, "Навыки", false),

        ParamNum("Пассивная мудрость(внимательность)", 0, false, null, 0, "Навыки", false),
        ParamNum("Медные", 0, false, null, 0, "Деньги", false),
        ParamNum("Серебрянные", 0, false, null, 0, "Деньги", false),
        ParamNum("Золотые", 0, false, null, 0, "Деньги", false),
        ParamNum("Платиновые", 0, false, null, 0, "Деньги", false),
    )

    val paramOptionsDnDSystem = RealmList<ParamOptions>(
        ParamOptions(
            "Значимость",
            "",
            false,
            "Выберите значимость",
            RealmList<String>("Главный герой", "Второстепенный герой"),
            "Базовые параметры", false
        ),
        ParamOptions(
            "Класс",
            "",
            false,
            "Выберите класс",
            RealmList<String>(
                "Колдун",
                "Бард",
                "Варвар",
                "Воин",
                "Волшебник",
                "Друид",
                "Жрец",
                "Изобретатель",
                "Монах",
                "Паладин",
                "Плут",
                "Следопыт",
                "Чародей"
            ),
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
        ParamOptions(
            "Раса",
            "",
            false,
            "Выберите класс",
            RealmList<String>(
                "Эльф",
                "Дварф",
                "Гном",
                "Человек",
                "Полурослик",
                "Драконорожденный",
                "Аракокра",
                "Тифлинг",
                "Голиаф",
                "Дженази",
                "Полуорк"
            ),
            "Базовые параметры",
            false
        ),
        ParamOptions(
            "Мировоззрение",
            "",
            false,
            "Выберите мировоззрение",
            RealmList<String>(
                "Законопослушный-добрый",
                "Законопослушный-нейтральный",
                "Законопослушный-злой",
                "Истинно-добрый",
                "Истинно-нейтральный",
                "Истинно-злой",
                "Хаотично-добрый",
                "Хаотично-нейтральный",
                "Хаотично-злой"
            ),
            "Базовые параметры",
            false
        ),
    )

    val paramStrDnDSystem = RealmList<ParamStr>(
        ParamStr("Имя персонажа", "", removable = false, "Базовые параметры", false),
        ParamStr("Название игры", "", removable = false),
        ParamStr("Кость хитов", "1д6", removable = false, "Базовые параметры", false),
        ParamStr("Черты характера", "", removable = false, "Биография", false),
        ParamStr("Идеалы", "", removable = false, "Биография", false),
        ParamStr("Привязанности", "", removable = false, "Биография", false),
        ParamStr("Слабости", "", removable = false, "Биография", false),
    )


    val DnDSystem = GameSystem(
        2,
        "DnDSystem",
        RealmList<String>(
            "Базовые параметры",
            "Параметры",
            "Навыки",
            "Биография",
            "Особенности персонажа",
            "Информация о игроке",
            "Снаряжение",
            "Атаки и заклинания",
            "Экипированное",
            "Спасброски",
            "Деньги"
        ),
        RealmList<RealmPair>(
            RealmPair(
                "Оружие",
                RealmList<String>(
                    "Простое рукопашное",
                    "Простое дальнобойное",
                    "Воинское рукопашное",
                    "Воинское дальнобойное"
                )
            ),
            RealmPair(
                "Броня",
                RealmList<String>("Лёгкий доспех", "Средний доспех", "Тяжёлый доспех", "Щит")
            ),
            RealmPair(
                "Снаряжение",
                RealmList<String>("Прочее", "Инструменты", "Зелья", "Артефакты")
            ),
        ),
        typesAttack = RealmList(
            "Дробящий",
            "Колющий",
            "Рубящий",
            "Излучением",
            "Некротический",
            "Звуком",
            "Психический"
        ),
        // где первая строка - название группы, вторая название типа
        tabsToGroup = RealmList<RealmSomething>(
            RealmSomething(RealmList("Базовые параметры")),
            RealmSomething(RealmList("Параметры", "Навыки", "Спасброски")),
            RealmSomething(RealmList("Экипированное", "Атаки и заклинания")),
            RealmSomething(RealmList("Снаряжение", "Деньги")),
            RealmSomething(RealmList("Особенности персонажа", "Информация о игроке", "Биография")),
        ),
        templateCharacter = RealmList<Character>(
            Character(
                4,
                0,
                initGroupsParam(
                    mapOf(
                        "Базовые параметры" to mapOf(//название группы
                            "listParamStr" to mapOf(
                                //тип листа
                                "Имя персонажа" to "Кетчбурьмек",//название параметра и значение
                                "Кость хитов" to "1д8",

                                ),
                            "listParamNum" to mapOf(
                                "Возраст" to "30",
                                "Уровень" to "80",
                                "Опыт" to "0",
                                "Вдохновение" to "0",
                                "Бонус мастерства" to "15",
                                "Текущие хиты" to "16",
                                "Временные хиты" to "2",
                                "Максимальные хиты" to "18",
                                "Бонус к инициативе" to "0",
                                "Скорость" to "30",
                                "Класс доспеха" to "18",
                            ),
                            "listParamOptions" to mapOf(
                                "Значимость" to "Главный герой",
                                "Класс" to "Изобретатель",
                                "Раса" to "Гном",
                                "Пол" to "Мужской",
                                "Мировоззрение" to "Законопослушный-злой",
                            )
                        ),
                        "Параметры" to mapOf(
                            "listParamNum" to mapOf(
                                "Сила" to "7",
                                "Ловкость" to "7",
                                "Телосложение" to "7",
                                "Интеллект" to "7",
                                "Мудрость" to "7",
                                "Харизма" to "7",
                            ),
                        ),
                        "Навыки" to mapOf(
                            "listParamNum" to mapOf(
                                "Акробатика" to "2",
                                "Анализ" to "2",
                                "Атлетика" to "2",
                                "Внимательность" to "2",
                                "Выживание" to "2",
                                "Выступление" to "2",
                                "Запугивание" to "2",
                                "Истроия" to "2",
                                "Ловкость рук" to "2",
                                "Магия" to "2",
                                "Проницательность" to "2",
                                "Религия" to "2",
                                "Скрытность" to "2",
                                "Убеждение" to "2",
                                "Уход за животными" to "2",
                            ),
                        ),
                        "Спасброски" to mapOf(
                            "listParamNum" to mapOf(
                                "Сила спасбросок" to "7",
                                "Ловкость спасбросок" to "7",
                                "Телосложение спасбросок" to "7",
                                "Интеллект спасбросок" to "7",
                                "Мудрость спасбросок" to "7",
                                "Харизма спасбросок" to "7",
                            ),
                        ),
                        "Экипированное" to mapOf(
                            "listItem" to mapOf(

                            ),
                        ),
                        "Атаки и заклинания" to mapOf(
                            "listItem" to mapOf(

                            ),
                        ),
                        "Снаряжение" to mapOf(
                            "listItem" to mapOf(
                                "mapItem" to "100 евробаксов немаркированными купюрами",
                            ),
                        ),
                        "Деньги" to mapOf(
                            "listParamNum" to mapOf(
                                "Медные" to "8",
                                "Серебрянные" to "6",
                                "Золотые" to "2",
                                "Платиновые" to "3",
                            ),
                        ),
                        "Биография" to mapOf(
                            "listParamStr" to mapOf(
                                "Черты характера" to "",
                                "Идеалы" to "",
                                "Привязанности" to "",
                                "Слабости" to "",
                            ),
                        ),
                    ),
                    paramStrDnDSystem,
                    paramNumDnDSystem,
                    paramOptionsDnDSystem,
                    RealmList<Item>()
                )
            )
        ),
        templateItem = RealmList<Item>(),
        templateParamNum = paramNumDnDSystem,
        templateParamStr = paramStrDnDSystem,
        templateParamOptions = paramOptionsDnDSystem,
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