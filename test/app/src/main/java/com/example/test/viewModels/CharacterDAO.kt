package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data_base.*
import io.realm.Realm
import io.realm.RealmList

open class CharacterDAO : ViewModel() {

    /*
    Небольшая инструкция по применению во избежание недопонимания
    Убедитесь, что в вашем characterListAdapter применяется инициализация characterList, gameId,
    после перехода к персонажу устанавливается characterId
     */

    // Подключаем Realm
    private var realm: Realm = Realm.getDefaultInstance()

    // Общие данные для presentGame

    var characterId = 0

    fun initCharacterId(id: Int) {
        characterId = id
    }

    var gameId = 0

    fun initGameId(id: Int) {
        gameId = id
    }

    // Основные переменные для Реалма
    val characterList = MutableLiveData<RealmList<Character>>(RealmList<Character>())

    val item = MutableLiveData<Item>(Item())

    //CREATE////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun getNewCharacterId(): Int {
        var id = realm.where(Character::class.java).max("id")?.toInt()
        if (id == null) {
            id = 1
        } else {
            id += 1
        }
        return id
    }

    fun addCharacter(
        gameId: Int,
        attributes: RealmList<GroupParam>
    ) {

        val character = Character(
            getNewCharacterId(), gameId, attributes
        )
        realm.executeTransaction { transactionRealm ->
            transactionRealm.insert(character)
        }
        characterList.value!!.add(character)
    }

    //READ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    fun loadCharactersByGameId(id: Int) {
        val res = realm.where(Character::class.java).equalTo("gameId", id).findAll()
        res.forEach {
            characterList.value!!.add(it)
        }
    }

    //UPDATE////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // работа с локальным и временным

    fun LOCitemClear(){
        realm.executeTransaction {
            item.value = Item()
        }
    }

    fun LOCaddEffectWeaponItem(effectWeapon: EffectWeapon){
        realm.executeTransaction {
            item.value!!.effectsWeapon.add( effectWeapon)
        }
    }

    fun LOCupdateEffectWeaponItem(indexEff:Int, effectWeapon: EffectWeapon){
        realm.executeTransaction {
            item.value!!.effectsWeapon[indexEff] = effectWeapon
        }
    }

    fun LOCupdateEffectAddItem(indexEff:Int, effectAdd: EffectAdd){
        realm.executeTransaction {
            item.value!!.effectsAdd[indexEff] = effectAdd
        }
    }

    fun LOCaddEffectAddItem(effectAdd: EffectAdd){
        realm.executeTransaction {
            item.value!!.effectsAdd.add(effectAdd)
        }
    }

    fun LOCupdateParamStrItem(indexParam:Int, param: ParamStr){
        realm.executeTransaction {
            item.value!!.otherParamStr[indexParam] = param
        }
    }

    fun LOCaddParamStrItem(param: ParamStr){
        realm.executeTransaction {
            item.value!!.otherParamStr.add(param)
        }
    }

    fun LOCupdateParamOptionsItem(indexParam:Int, param: ParamOptions) {
        realm.executeTransaction {
            item.value!!.otherParamOptions[indexParam] = param
        }
    }

    fun LOCaddParamOptionsItem(param: ParamOptions) {
        realm.executeTransaction {
            item.value!!.otherParamOptions.add(param)
        }
    }

    fun LOCupdateParamNumItem(indexParam:Int, param: ParamNum) {
        realm.executeTransaction {
            item.value!!.otherParamNum[indexParam] = param
        }
    }

    fun LOCaddParamNumItem(param: ParamNum) {
        realm.executeTransaction {
            item.value!!.otherParamNum.add(param)
        }
    }

    fun LOCupdateNameItem(name:String) {
        realm.executeTransaction {
            item.value!!.name = name
        }
    }

    fun LOCupdateDescriptionItem(description:String) {
        realm.executeTransaction {
            item.value!!.description = description
        }
    }
    //

    fun updateCharacterParamStr(
        characterId: Int, value: String,
        groupTitle: String, paramName: String
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        val param = group?.attributes?.listParamStr?.singleOrNull { pn ->
            pn.name == paramName
        }
        realm.executeTransaction {
            param?.value = value
        }
    }

    fun updateCharacterParamNum(
        characterId: Int, value: Int,
        groupTitle: String, paramName: String
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        val param = group?.attributes?.listParamNum?.singleOrNull { pn ->
            pn.name == paramName
        }
        realm.executeTransaction {
            param?.value = value
        }
    }

    fun updateCharacterParamOptions(
        characterId: Int, value: String,
        groupTitle: String, paramName: String
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        val param = group?.attributes?.listParamOptions?.singleOrNull { pn ->
            pn.name == paramName
        }
        realm.executeTransaction {
            param?.value = value
        }
    }

    fun updateCharacterParamItem(
        characterId: Int, value: Item,
        groupTitle: String, index: Int
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        realm.executeTransaction {
            if (group?.attributes?.listItem?.get(index) != null){
            group.attributes!!.listItem[index] = value
            }
        }
    }

    fun updateEffectWeapon(
        id: Int,
        titleGroup: String,
        indexItem: Int,
        indexEff: Int,
        name: String,
        type: String,
        numCount: Int,
        dX: Int,
        wearout: Int
    ) {
        val list = characterList.value!!.singleOrNull { character ->
            character.id == id
        }?.attributes?.singleOrNull { gp ->
            gp.title == titleGroup
        }?.attributes?.listItem?.get(indexItem)?.effectsWeapon
        if (list != null) {
            val effect = list[indexEff]!!
            realm.executeTransaction {
                effect.name = name
                effect.fightType = type
                effect.numCount = numCount
                effect.dX = dX
                effect.wearout = (if (wearout != 0) wearout else null)
            }
        }
    }

    fun addEffectWeapon(
        id: Int,
        titleGroup: String,
        indexItem: Int,
        indexEff: Int,
        name: String,
        type: String,
        numCount: Int,
        dX: Int,
        wearout: Int
    ) {
        val list = characterList.value!!.singleOrNull { character ->
            character.id == id
        }?.attributes?.singleOrNull { gp ->
            gp.title == titleGroup
        }?.attributes?.listItem?.get(indexItem)?.effectsWeapon
        if (list != null) {
            if (list.size >= indexEff || indexEff < 0) {// новый
                val effect =
                    EffectWeapon(name, type, numCount, dX, (if (wearout != 0) wearout else null))
                realm.executeTransaction {
                    list.add(effect)
                }
            }
        }
    }

    fun updateEffectAdd(
        id: Int,
        titleGroup: String,
        indexItem: Int,
        indexEff: Int,
        permanent: Boolean,
        property: String,
        impact: Int,
        sign: Boolean,
        duration: Int? = null,
        rollback: Int? = null
    ) {
        val list = characterList.value!!.singleOrNull { character ->
            character.id == id
        }?.attributes?.singleOrNull { gp ->
            gp.title == titleGroup
        }?.attributes?.listItem?.get(indexItem)?.effectsAdd
        if (list != null) {
            val effect = list[indexEff]!!
            realm.executeTransaction {
                effect.permanent = permanent
                effect.property = property
                effect.impact = impact
                effect.sign = sign
                effect.duration = duration
                effect.rollback = rollback
            }
        }
    }

    fun addEffectAdd(
        id: Int,
        titleGroup: String,
        indexItem: Int,
        permanent: Boolean,
        property: String,
        impact: Int,
        sign: Boolean,
        duration: Int? = null,
        rollback: Int? = null
    ) {
        val list = characterList.value!!.singleOrNull { character ->
            character.id == id
        }?.attributes?.singleOrNull { gp ->
            gp.title == titleGroup
        }?.attributes?.listItem?.get(indexItem)?.effectsAdd
        if (list != null) {
            val effect =
                EffectAdd(permanent, property, impact, sign, duration, rollback)
            realm.executeTransaction {
                list.add(effect)
            }
        }
    }

    fun addCharacterItem(
        characterId: Int,
        groupTitle: String,
        item: Item
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        realm.executeTransaction {
            group?.attributes?.listItem?.add(item)
        }
    }

    fun addCharacterParamStr(
        characterId: Int,
        groupTitle: String, paramStr: ParamStr
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        realm.executeTransaction {
            group?.attributes?.listParamStr?.add(paramStr)
        }
    }

    fun addCharacterParamNum(
        characterId: Int,
        groupTitle: String, param: ParamNum
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        realm.executeTransaction {
            group?.attributes?.listParamNum?.add(param)
        }
    }

    fun addCharacterParamOptions(
        characterId: Int,
        groupTitle: String, param: ParamOptions
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        realm.executeTransaction {
            group?.attributes?.listParamOptions?.add(param)
        }
    }

    fun updateItemParamNum(
        id: Int,
        titleGroup: String,
        indexItem: Int,
        indexParam: Int,
        param: ParamNum
    ) {
        val list = characterList.value!!.singleOrNull { character ->
            character.id == id
        }?.attributes?.singleOrNull { gp ->
            gp.title == titleGroup
        }?.attributes?.listItem?.get(indexItem)?.otherParamNum
        if (list != null) {
            realm.executeTransaction {
                list[indexParam] = param
            }
        }
    }

    fun updateItemParamStr(
        id: Int,
        titleGroup: String,
        indexItem: Int,
        indexParam: Int,
        param: ParamStr
    ) {
        val list = characterList.value!!.singleOrNull { character ->
            character.id == id
        }?.attributes?.singleOrNull { gp ->
            gp.title == titleGroup
        }?.attributes?.listItem?.get(indexItem)?.otherParamStr
        if (list != null) {
            realm.executeTransaction {
                list[indexParam] = param
            }
        }
    }

    fun updateItemParamOption(
        id: Int,
        titleGroup: String,
        indexItem: Int,
        indexParam: Int,
        param: ParamOptions
    ) {
        val list = characterList.value!!.singleOrNull { character ->
            character.id == id
        }?.attributes?.singleOrNull { gp ->
            gp.title == titleGroup
        }?.attributes?.listItem?.get(indexItem)?.otherParamOptions
        if (list != null) {
            realm.executeTransaction {
                list[indexParam] = param
            }
        }
    }

    fun addItemParamNum(
        characterId: Int,
        groupTitle: String, itemId: Int, paramNum: ParamNum
    ) {
        val list = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }?.attributes?.listItem?.get(itemId)?.otherParamNum
        if (list != null) {
            realm.executeTransaction {
                list.add(paramNum)
            }
        }
    }

    fun addItemParamString(
        characterId: Int,
        groupTitle: String, itemId: Int, paramStr: ParamStr
    ) {
        val list = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }?.attributes?.listItem?.get(itemId)?.otherParamStr
        if (list != null) {
            realm.executeTransaction {
                list.add(paramStr)
            }
        }
    }

    fun addItemParamOptions(
        characterId: Int,
        groupTitle: String, itemId: Int, paramOptions: ParamOptions
    ) {
        val list = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }?.attributes?.listItem?.get(itemId)?.otherParamOptions
        if (list != null) {
            realm.executeTransaction {
                list.add(paramOptions)
            }
        }
    }

    //DELETE////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    fun deleteCharacterParamItem(
        characterId: Int,
        groupTitle: String, itemName: String
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        val param = group?.attributes?.listItem?.singleOrNull { pn ->
            pn.name == itemName
        }
        realm.executeTransaction {
            group?.attributes?.listItem?.remove(param)
        }
    }

    fun deleteCharacterParamStr(
        characterId: Int,
        groupTitle: String, paramName: String
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        val param = group?.attributes?.listParamStr?.singleOrNull { pn ->
            pn.name == paramName
        }
        realm.executeTransaction {
            group?.attributes?.listParamStr?.remove(param)
        }
    }

    fun deleteCharacterParamNum(
        characterId: Int,
        groupTitle: String, paramName: String
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        val param = group?.attributes?.listParamNum?.singleOrNull { pn ->
            pn.name == paramName
        }
        realm.executeTransaction {
            group?.attributes?.listParamNum?.remove(param)
        }
    }

    fun deleteCharacterParamOptions(
        characterId: Int,
        groupTitle: String, paramName: String
    ) {
        val character = characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }
        val group = character?.attributes?.singleOrNull { gp ->
            gp.title == groupTitle
        }
        val param = group?.attributes?.listParamOptions?.singleOrNull { pn ->
            pn.name == paramName
        }
        realm.executeTransaction {
            group?.attributes?.listParamOptions?.remove(param)
        }
    }

    fun deleteEffectWeapon(id: Int, titleGroup: String, indexItem: Int, indexEff: Int) {
        realm.executeTransaction {
            characterList.value!!.singleOrNull { character ->
                character.id == id
            }?.attributes?.singleOrNull { gp ->
                gp.title == titleGroup
            }?.attributes?.listItem?.get(indexItem)?.effectsWeapon?.deleteFromRealm(indexEff)
        }
    }

    fun deleteEffectAdd(id: Int, titleGroup: String, indexItem: Int, indexEff: Int) {
        realm.executeTransaction {
            characterList.value!!.singleOrNull { character ->
                character.id == id
            }?.attributes?.singleOrNull { gp ->
                gp.title == titleGroup
            }?.attributes?.listItem?.get(indexItem)?.effectsAdd?.deleteFromRealm(indexEff)
        }
    }

    fun deleteItemParamStr(id: Int, titleGroup: String, indexItem: Int, indexParam:Int) {
        realm.executeTransaction {
            characterList.value!!.singleOrNull { character ->
                character.id == id
            }?.attributes?.singleOrNull { gp ->
                gp.title == titleGroup
            }?.attributes?.listItem?.get(indexItem)?.otherParamStr?.deleteFromRealm(indexParam)
        }
    }

    fun deleteItemParamNum(id: Int, titleGroup: String, indexItem: Int, indexParam:Int) {
        realm.executeTransaction {
            characterList.value!!.singleOrNull { character ->
                character.id == id
            }?.attributes?.singleOrNull { gp ->
                gp.title == titleGroup
            }?.attributes?.listItem?.get(indexItem)?.otherParamNum?.deleteFromRealm(indexParam)
        }
    }

    fun deleteItemParamOption(id: Int, titleGroup: String, indexItem: Int, indexParam:Int) {
        realm.executeTransaction {
            characterList.value!!.singleOrNull { character ->
                character.id == id
            }?.attributes?.singleOrNull { gp ->
                gp.title == titleGroup
            }?.attributes?.listItem?.get(indexItem)?.otherParamOptions?.deleteFromRealm(indexParam)
        }
    }

    fun clearCharactersByGameId(gameId: Int) {
        val listOfCharacter =
            realm.where(Character::class.java).equalTo("gameId", gameId).findAll()
        realm.executeTransaction {
            listOfCharacter.deleteAllFromRealm()
        }
        characterList.value!!.clear()
    }

    fun deleteCharacter(characterId: Int) {
        realm.executeTransaction {
            val character = characterList.value!!.singleOrNull {
                it.id == characterId
            }
            characterList.value!!.remove(character)
            val characterRealm = realm.where(Character::class.java).equalTo("id", characterId).findFirst()
            characterRealm?.deleteFromRealm()
        }
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

}