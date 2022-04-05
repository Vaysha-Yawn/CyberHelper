package com.example.test.data_base

import io.realm.RealmList
import io.realm.RealmMap
import io.realm.RealmObject

open class FightType(
    ////////// общее
    var name:String = "",
    var forAllOrItem:Boolean = true,// false for all true for item

    ////////// как расчитывается сложность
    var nameDiff:String = "",//
    var difficult:String = "",
    var difParameters:RealmList<String> = RealmList<String>(),// названия навыков или пармеров, которые влияют на бросок защищающегося
   //////
    var settingsRoll: SettingsRoll? = SettingsRoll(),
  ////// если выбрано ролл несколько героев, то необходимо уточнить, как именно будет высчитываться сумма броска: сумма, отдельно,среднее,хотябы N%. 
  
  ////// здесь мы выбираем одну из нескольких таблиц или создаем новую, если это нужно
  var tableDiff:String? = null,
  
    /////////// как производится бросок героя
    var nameRoll:String = "",//
    var roll: String = "",//
    var rollParameters:RealmList<String> = RealmList<String>(),// названия навыков или пармеров, которые влияют на бросок атакующего
      var tableRoll:String? = null,
  
    //////////// что происходит в случае успешного прохождения проверки
    var successComment: String? = "",// если нулловый, тогда нет
    var successEffectAdd: RealmList<EffectAdd>? = RealmList<EffectAdd>(),// effectAttack
    // надо изменить effectAdd илм сделать другой под него., мне кажется, что именно здесь необходимо расписывать, как будет влиять, сколько кубиков кидать, а если указано, что оно делегированно, то использовать делегированное значение
    
    ///////////// что происходит в случае провала
    var failComment: String? = "",// есть чекбокс ложь, то этот параметр нулловый
    var failEffectAdd: RealmList<EffectAdd>? = RealmList<EffectAdd>(),
    
    ///////////// что происходит в случае ничьей
    var nothingComment: String? = "",// есть чекбокс ложь, то этот параметр нулловый
    var nothingEffectAdd: RealmList<EffectAdd>? = RealmList<EffectAdd>(),
    
    var variations:RealmList<String> = RealmList()
):RealmObject()

open class SettingsRoll(
    var critical:Boolean = true,
    var advantageAndDisadvantage:Boolean = true,// true - on, false - off ; возможность задавать преимущество или помеху
    var difRollByD:Int? = 10,// null если бросок не нужен для сложности, число - максимальная грьбика, прим 1Д10
    var modificators:Boolean = true,// возможность ставить и учитывать модификаторы
):RealmObject()

open class Variation(
    var effect :  RealmList<String>? = null,// описывает эффект на какой параметр, и какой эффект, пример "Difficult" "+" "6", "Прямой Урон" "*" "2"
    var textTrue: String = "",
    var textFalse: String = ""
) :RealmObject()


// тогда необходимо для простоты доступа везде вместо названия типа аттаки использовать экземпляр этого класса

open class TableForFight(
  var columns: String ="",// группу опций или без колонок
  var rows:RealmList<String> = RealmList<String>(),
  //var columnsToRowsToValue: RealmMap<String, RealmMap<String, Int>>
):RealmObject()


// по сути это двумерный массив, можно и больше,но зачем

