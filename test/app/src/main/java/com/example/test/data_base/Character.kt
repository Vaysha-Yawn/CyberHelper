package com.example.test.data_base

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Character(
    @PrimaryKey
    var id: Int = 0,
    var gameId:Int = 0,

    var attributes: RealmList<GroupParam> = RealmList<GroupParam>(),
    /*
    //Атрибуты здоровья
    var healthPoint: Int = 0,
    var hardRan:Int = 0,
    var checkVsDeath:Int = 0,

    //Параметры
    var intel:Int = 0,
    var ref:Int = 0,// реакция
    var dex:Int = 0,// ловкость
    var tech:Int = 0,// техника
    var cool:Int = 0,//  харизма
    var will:Int = 0,// воля
    var luck:Int = 0,// удача
    var move:Int = 0,// скорость
    var body:Int = 0,// телосложение
    var emp:Int = 0,// эмпатия

    //Навыки
    var Perception:Int = 0,// Внимательность
    var Tracking:Int = 0,// Выслеживание
    var Education:Int = 0,// Образование
    var Local_Expert:Int = 0,// Знание местности
    var Interface:Int = 0,// Интерфейс
    var Marksmanship:Int = 0,// Стрельба
    var Driving:Int = 0,// Вождение
    var Evasion:Int = 0,// Уклонение
    var Athletics:Int = 0,// Атлетика
    var Stealth:Int = 0,// Скрытность
    var Brawling:Int = 0,// Рукопашный бой
    var Melee_Weapon:Int = 0,// Оружие ближнего боя
    var Basic_Tech:Int = 0,// Знание техники
    var Cybertech:Int = 0,// Кибертехника
    var First_Aid:Int = 0,// Первая помощь
    var Bribery:Int = 0,// Подкуп
    var Interrogation:Int = 0,// Допрос
    var Persuasion:Int = 0,// Убеждение
    var Concentration:Int = 0,// Концентрация
    var Conversation:Int = 0,// Общение
    var Human_Perception:Int = 0,// Проницательность
    var Play_Instrument:Int = 0,// Игра на инструменте

    //Броня
    var armorHead: Int = 0,
    var armorBody:Int = 0,
    //Оружие
    var weapons: RealmList<Weapon> = RealmList(),

    //Прочая информация
    var role: String = "",// рокер, соло
    var gender: String = "",// пол мужчина, женщина
    var importance:String = "",// Второстепенный, главный, враг
    var age : Int = 0, // Возраст

    //Биография
    var description:String = "",// Общее описание
    var backgroundStory:String = "",// Прошлое
    var family:String = "", // Семья
    var motivation:String = "", //
    var allGoals:String = "", //
    var friends:String = "", //
    var enemies:String = "", //
    var romance:String = "", //
    var personality:String = "", //

    var programs: RealmList<Program> = RealmList(),
    var cyberware: RealmList<Item> = RealmList(),// импланты
    var gear: RealmList<Item> = RealmList(),// прочее снаряжение

    var reputation:RealmList<Deed> = RealmList(),

     */

):RealmObject()

