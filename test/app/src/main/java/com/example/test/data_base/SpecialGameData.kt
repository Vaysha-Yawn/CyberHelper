package com.example.test.data_base

class SpecialGameData() {
// убрать emptyList кроме item
    val groupPreferences = mapOf(
        "Базовые параметры" to mapOf(
            "str" to emptyList(), "num" to emptyList(), "options" to emptyList()
        ),
        "Броня" to mapOf(
            "num" to listOf("Броня для головы", "Броня для тела"),
        ),
        "Оружие" to mapOf(
            "item" to emptyList()
        ),
        "Параметры" to mapOf(
            "num" to listOf (
                "Интеллект" ,
                "Воля" ,
                "Харизма" ,
                "Эмпатия" ,
                "Техника" ,
                "Реакция" ,
                "Удача" ,
                "Телосложение" ,
                "Ловкость" ,
                "Скорость" ),
        ),
        "Навыки" to mapOf(
            "num" to listOf (
                "Внимательность" ,
                "Выслеживание" ,
                "Образование" ,
                "Знание местности" ,
                "Интерфейс" ,
                "Стрельба" ,
                "Вождение" ,
                "Уклонение",
                "Атлетика" ,
                "Скрытность" ,
                "Рукопашный бой",
                "Оружие ближнего боя" ,
                "Знание техники",
                "Кибертехника" ,
                "Первая помощь" ,
                "Подкуп",
                "Допрос",
                "Убеждение",
                "Концентрация" ,
                "Общение" ,
                "Проницательность",
                "Игра на инструменте",),
        ),
        "Киберимпланты" to mapOf(
            "item" to emptyList()
        ),
        "Снаряжение" to mapOf(
            "item" to emptyList()
        ),
        "Программы" to mapOf(
            "item" to emptyList()
        ),
        "Репутация" to mapOf(
            "num" to listOf("Итого репутация"),
            "item" to listOf("Поступок")
        ),
        "Биография" to mapOf(
            "str" to listOf("Семья", "Мотивация")
        ),
    )

    val difficultName = arrayListOf<String>(
        "Элементарный", "Бытовой", "Средний", "Незаурядный", "Невероятный", "Легендарный"
    )

    val difficultValue = listOf<String>(
        "10", "14", "18", "22", "25", "30"
    )

    val modName = arrayListOf<String>(
        "Непривычные инструменты, оружие или транспорт (-4)", "Непонятно, как выполнить эту задачу (-2)",
        "Нет нужных инструментов или деталей (-2)", "Сложная задача (-3)", "Вы никогда этого не делали (-1)",
        "Стрессовые условия или нападение проотивника (-3)", "Усталость, алкогольное или наркотическое опьянение (-)",
        "Приходится действовать скрытно (-4)", "Выполнению задачи мешает дым или темнота (-4)"
    )

    val modValue = listOf<String>(
        "4", "2", "2", "3", "1", "3", "4", "4", "4"
    )
    val mapParameterToSkill = mapOf<String, String>(
        "Внимательность" to "Интеллект",
        "Выслеживание" to "Интеллект",
        "Образование" to "Интеллект",
        "Знание местности" to "Интеллект",
        "Интерфейс" to "",
        "Стрельба" to "Реакция",
        "Вождение" to "Реакция",
        "Уклонение" to "Ловкость",
        "Атлетика" to "Ловкость",
        "Скрытность" to "Ловкость",
        "Рукопашный бой" to "Ловкость",
        "Оружие ближнего боя" to "Ловкость",
        "Знание техники" to "Техника",
        "Кибертехника" to "Техника",
        "Первая помощь" to "Техника",
        "Подкуп" to "Харизма",
        "Допрос" to "Харизма",
        "Убеждение" to "Харизма",
        "Концентрация" to "Воля",
        "Общение" to "Эмпатия",
        "Проницательность" to "Эмпатия",
        "Игра на инструменте" to "Эмпатия",
    )
    // TODO: требуется дороботка, возможно объединю цель, модиф, 1д10 в один фрагмент, при выборе огонь на подавления несколько таких целей

/*    val mapFightTypeToFragment = mutableMapOf(
        "Рукопашный бой" to FightType (
              name:String,
         forAllOrItem:Boolean,
     difficult:String,
     roll: String,
     successCommentBoolean: Boolean,
     successCommentText: String,
     successEffectAdd: RealmList<EffectAdd>,
     successDamageBoolean: Boolean,
     failCommentBoolean: Boolean,
     failCommentText: String,
     failEffectAdd: RealmList<EffectAdd>,
    ),
        "Ближний бой" to mapOf("bodyOrHead", "DD goal one", "Modificators", "m1d10")
        , "Дальний бой" to mapOf("bodyOrHead", "difficultByGoalOrDistance",
            "DD goal one", "Modificators", "m1d10", "DD distance")
        , "Автоматический огонь" to mapOf("bodyOrHead", "difficultByGoalOrDistance",
            "DD goal one", "Modificators", "m1d10", "DD distance", "typeShoot", "howManyShoot")
        , "Взрывчатка" to mapOf("GoalCompactFragment")
    )*/

    val mapDifficultByDistance = mapOf(
        "Пистолет" to mapOf(
            "0-12 м" to 15,
            "13-25 м" to 20,
            "26-50 м" to 25,
            "51-100 м" to 30,
            "101-200 м" to 30,
        ),
        "Пистолет-пулемет" to mapOf(
            "0-12 м" to 15,
            "13-25 м" to 15,
            "26-50 м" to 20,
            "51-100 м" to 25,
            "101-200 м" to 25,
            "201-400 м" to 30,
        ),
        "Дробовик" to mapOf(
            "0-12 м" to 15,
            "13-25 м" to 20,
            "26-50 м" to 25,
            "51-100 м" to 30,
            "101-200 м" to 35,
        ),
        "Винтовка" to mapOf(
            "0-12 м" to 15,
            "13-25 м" to 10,
            "26-50 м" to 10,
            "51-100 м" to 15,
            "101-200 м" to 20,
            "201-400 м" to 25,
            "401-800 м" to 30,
        ),
        "Автомат" to mapOf(
            "0-12 м" to 15,
            "13-25 м" to 10,
            "26-50 м" to 10,
            "51-100 м" to 15,
            "101-200 м" to 20,
            "201-400 м" to 25,
            "401-800 м" to 30,
        ),
        "Ракетная установка" to mapOf(
            "0-12 м" to 15,
            "13-25 м" to 15,
            "26-50 м" to 15,
            "51-100 м" to 20,
            "101-200 м" to 20,
            "201-400 м" to 25,
            "401-800 м" to 30,
        )
    )

    val mapDifficultByDistanceThreeShotBurst = mapOf(
        "Пистолет-пулемет" to mapOf(
            "0-12 м" to 12,
            "13-25 м" to 15,
            "26-50 м" to 22,
            "51-100 м" to 28,
        ),
        "Автомат" to mapOf(
            "0-12 м" to 12,
            "13-25 м" to 10,
            "26-50 м" to 12,
            "51-100 м" to 18,
        ),
    )

}