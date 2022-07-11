package com.example.test.data_base.realm.other_realm_object

// в классе character будет лишь название типа боя, а его характеристики мы будем брать из массива со всеми типами боя
data class FightType(
    ////////// общее
    var name: String = "",
    var forAllOrItem: Boolean = true,// false for all true for item

    ////////// как расчитывается сложность
    var nameDiff: String = "",//
    var difficult: String = "",
    var difParameters: Map<String, String> = mapOf(),// названия навыков или пармеров, которые влияют на бросок защищающегося
    //////// указывать, как название группы, название параметра
    ////////
    var settingsRoll: SettingsRoll? = SettingsRoll(),

    ////// если выбрано ролл несколько героев, то необходимо уточнить, как именно будет высчитываться сумма броска: сумма, отдельно,среднее,хотябы N%.
    var howMerge: String?, // описывает, как мы должны слиять несколько различных бросков, например сложить их, найти среднее, проверить отдельно каждый, выстроить по убыванию и т.д.

    /////////// как производится бросок героя
    var nameRoll: String = "",//
    var roll: String = "",//
    var rollParameters: Map<String, String> = mapOf(),// названия навыков или пармеров, которые влияют на бросок атакующего
    // указывать, как название группы, название параметра

    //////////// что происходит в случае успешного прохождения проверки
    var successComment: String? = "",// если нулловый, тогда нет
    var successEffectAdd: List<EffectAdd>? = listOf(),// effectAttack
    // надо изменить effectAdd илм сделать другой под него., мне кажется, что именно здесь необходимо расписывать, как будет влиять, сколько кубиков кидать, а если указано, что оно делегированно, то использовать делегированное значение

    ///////////// что происходит в случае провала
    var failComment: String? = "",// есть чекбокс ложь, то этот параметр нулловый
    var failEffectAdd: List<EffectAdd>? = listOf(),

    ///////////// что происходит в случае ничьей
    var nothingComment: String? = "",// есть чекбокс ложь, то этот параметр нулловый
    var nothingEffectAdd: List<EffectAdd>? = listOf(),

    var variations: List<String> = listOf()
)

data class SettingsRoll(
    var critical: Boolean = true,
    var advantageAndDisadvantage: Boolean = true,// true - on, false - off ; возможность задавать преимущество или помеху
    var difRollByD: Int? = 10,// null если бросок не нужен для сложности, число - максимальная грань, прим 1Д10
    var modificators: Boolean = true,// возможность ставить и учитывать модификаторы
)

data class Variation(
    var effect: List<String>? = null,// описывает эффект на какой параметр, и какой эффект, пример "Difficult" "+" "6", "Прямой Урон" "*" "2"
    var textTrue: String = "",
    var textFalse: String = ""
)
// тогда необходимо для простоты доступа везде вместо названия типа аттаки использовать экземпляр этого класса

data class TableForFight(
    var columns: String = "",// группу опций или без колонок
    var rows: List<String> = listOf<String>(),
    var columnsToRowsToValue: Map<String, Map<String, Int>>
)
// по сути это двумерный массив, можно и больше,но зачем

data class DiffByTable(
    var tableRoll: String = "",
    // может что-то еще
)

data class Goal(
    var name: String = "",
    var characterId: Int = 0
)

data class Mod(
    var style: Boolean,
    var value: Int
)
