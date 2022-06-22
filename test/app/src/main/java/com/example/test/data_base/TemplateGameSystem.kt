package com.example.test.data_base

import com.example.test.test_data.DTemplateParamNum
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
            effectsDamage = RealmList<EffectDamage>(EffectDamage(
                "Киберрука",
                melle,
                numCount = 1,
                dX = 6
            )),
            group = "Оружие"
        ),
        Item(
            "Нож",
            "",
            true,
            effectsDamage = RealmList<EffectDamage>(EffectDamage("Нож", melle, numCount = 1, dX = 6)),
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
                    numCount = 2,
                    dX = 6
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
                    numCount = 2,
                    dX = 6
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
                    numCount = 2,
                    dX = 6
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
                    numCount = 2,
                    dX = 6
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
                    numCount = 2,
                    dX = 6
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
        ParamNum(
            name = "Здоровье",
            value = 0,
            removable = false,
            maxValue = 100,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Тяжелое ранение",
            value = 0,
            removable = false,
            maxValue = 50,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Испытание против смерти",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Базовые параметры", forItemOrCharacter = false
        ),

        ParamNum(
            name = "Возраст",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),

        ParamNum(
            name = "Деньги, евробаксы",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры", forItemOrCharacter = false
        ),

        ParamNum(
            name = "Броня для головы",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Броня",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Броня для тела",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Броня",
            forItemOrCharacter = false
        ),

        ParamNum(
            name = "Интеллект",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Воля",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Эмпатия",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Харизма",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Техника",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Реакция",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Удача",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Телосложение",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Ловкость",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Скорость",
            value = 0,
            removable = false,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),

        ParamNum(
            name = "Внимательность",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Выслеживание",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Образование",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Знание местности",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Интерфейс",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Стрельба",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Вождение",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Уклонение",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Атлетика",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Скрытность",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Рукопашный бой",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Оружие ближнего боя",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Знание техники",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Кибертехника",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Первая помощь",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Подкуп",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Допрос",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Убеждение",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Концентрация",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Общение",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Проницательность",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Игра на инструменте",
            value = 0,
            removable = true,
            maxValue = 10,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),

        ParamNum(
            name = "Итого репутация",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Репутация",
            forItemOrCharacter = false
        ),
    )

    val paramOptionsCyberPuckSystem = RealmList<ParamOptions>(
        ParamOptions(
            name = "Тип оружия",
            value = "",
            removable = false,
            defMain = "Выберите тип оружия",
            options = RealmList<String>("Ближний бой", "Дальний бой", "Автоматический огонь", "Взрывчатка"),
            currentGroup = "",
            forItemOrCharacter = true
        ),
        ParamOptions(
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
            ), currentGroup = "", forItemOrCharacter = true
        ),
        ParamOptions(
            name = "Значимость",
            value = "",
            removable = false,
            defMain = "Выберите значимость",
            options = RealmList<String>("Главный герой", "Второстепенный герой"),
            currentGroup = "Базовые параметры", forItemOrCharacter = false
        ),
        ParamOptions(
            name = "Роль",
            value = "",
            removable = false,
            defMain = "Выберите роль",
            options = RealmList<String>("Рокер", "Соло", "Нетраннер", "Кочевник", "Техник", "Фиксер"),
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamOptions(
            name = "Пол",
            value = "",
            removable = false,
            defMain = "Выберите пол",
            options = RealmList<String>("Мужской", "Женский"),
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
    )
    val paramStrCyberPuckSystem = RealmList<ParamStr>(
        ParamStr(
            name = "Имя персонажа",
            value = "",
            removable = false,
            forItemOrCharacter = false
        ),
        ParamStr(name = "Название игры", value = "", removable = false),
        ParamStr(
            name = "Семья",
            value = "",
            removable = false,
            forItemOrCharacter = false
        ),
        ParamStr(
            name = "Мотивация",
            value = "",
            removable = false,
            forItemOrCharacter = false
        ),
        ParamStr(
            name = "Особенность",
            value = "",
            removable = true,
            forItemOrCharacter = true
        ),
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
        ParamNum(
            name = "Возраст",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Уровень",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Опыт",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),

        ParamNum(
            name = "Вдохновение",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Бонус мастерства",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),

        ParamNum(
            name = "Текущие хиты",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Временные хиты",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Максимальные хиты",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Бонус к инициативе",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Скорость",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),

        ParamNum(
            name = "Класс доспеха",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),

        ParamNum(
            name = "Сила",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Ловкость",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Телосложение",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Интеллект",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Мудрость",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Харизма",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Параметры",
            forItemOrCharacter = false
        ),

        ParamNum(
            name = "Сила спасбросок",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Спасброски",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Ловкость спасбросок",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Спасброски",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Телосложение спасбросок",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Спасброски",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Интеллект спасбросок",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Спасброски",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Мудрость спасбросок",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Спасброски",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Харизма спасбросок",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Спасброски",
            forItemOrCharacter = false
        ),

        ParamNum(
            name = "Акробатика",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Анализ",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Атлетика",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Внимательность",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Выживание",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Выступление",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Запугивание",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Истроия",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Ловкость рук",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Магия",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Проницательность",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Религия",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Скрытность",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Убеждение",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Уход за животными",
            value = 0,
            removable = false,
            maxValue = 30,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),

        ParamNum(
            name = "Пассивная мудрость(внимательность)",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Навыки",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Медные",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Деньги",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Серебрянные",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Деньги",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Золотые",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Деньги",
            forItemOrCharacter = false
        ),
        ParamNum(
            name = "Платиновые",
            value = 0,
            removable = false,
            maxValue = null,
            minValue = 0,
            currentGroup = "Деньги",
            forItemOrCharacter = false
        ),
    )

    val paramOptionsDnDSystem = RealmList<ParamOptions>(
        ParamOptions(
            name = "Значимость",
            value = "",
            removable = false,
            defMain = "Выберите значимость",
            options = RealmList<String>("Главный герой", "Второстепенный герой"),
            currentGroup = "Базовые параметры", forItemOrCharacter = false
        ),
        ParamOptions(
            name = "Класс",
            value = "",
            removable = false,
            defMain = "Выберите класс",
            options = RealmList<String>(
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
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamOptions(
            name = "Пол",
            value = "",
            removable = false,
            defMain = "Выберите пол",
            options = RealmList<String>("Мужской", "Женский"),
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamOptions(
            name = "Раса",
            value = "",
            removable = false,
            defMain = "Выберите класс",
            options = RealmList<String>(
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
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
        ParamOptions(
            name = "Мировоззрение",
            value = "",
            removable = false,
            defMain = "Выберите мировоззрение",
            options = RealmList<String>(
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
            currentGroup = "Базовые параметры",
            forItemOrCharacter = false
        ),
    )

    val paramStrDnDSystem = RealmList<ParamStr>(
        ParamStr(
            name = "Имя персонажа",
            value = "",
            removable = false,
            forItemOrCharacter = false
        ),
        ParamStr(name = "Название игры", value = "", removable = false),
        ParamStr(
            name = "Кость хитов",
            value = "1д6",
            removable = false,
            forItemOrCharacter = false
        ),
        ParamStr(
            name = "Черты характера",
            value = "",
            removable = false,
            forItemOrCharacter = false
        ),
        ParamStr(
            name = "Идеалы",
            value = "",
            removable = false,
            forItemOrCharacter = false
        ),
        ParamStr(
            name = "Привязанности",
            value = "",
            removable = false,
            forItemOrCharacter = false
        ),
        ParamStr(
            name = "Слабости",
            value = "",
            removable = false,
            forItemOrCharacter = false
        ),
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