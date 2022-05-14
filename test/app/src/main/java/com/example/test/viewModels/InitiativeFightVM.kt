package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data_base.Character
import com.example.test.data_base.InitiativeFight
import io.realm.RealmList

class InitiativeFightVM() : ViewModel() {

    private val DAO = InitiativeFightDAO()

    val fightList = MutableLiveData<RealmList<InitiativeFight>>(RealmList<InitiativeFight>())

    fun loadList(gameId: Int){
        fightList.value = DAO.loadByGameId(gameId)
    }

    fun addInitiativeFight(
        gameId:Int,
        nameFight: String,
        listIdCharacter: RealmList<Int>
    ) {
        val initiativeFight = DAO.addInitiativeFight(
            gameId,
            nameFight,
            listIdCharacter
        )
        fightList.value!!.add(initiativeFight)
    }

    fun deleteInitiativeFight(id: Int){
        val initiativeFight = fightList.value!!.singleOrNull {
            it.id == id
        }
        fightList.value!!.remove(initiativeFight)
        DAO.deleteInitiativeFight(id)
    }

    fun findFightCharacter(listFight: RealmList<InitiativeFight>, listCharacter: RealmList<Character>)
    : MutableMap<String, MutableList<Character>>{
        val map = mutableMapOf<String, MutableList<Character>>()
        for ( fight in listFight){
            map[fight.nameFight] = mutableListOf()
            for (id in fight.listIdCharacter){
                listCharacter.singleOrNull {
                    it.id == id
                }.let {
                    if (it!=null){
                        map[fight.nameFight]?.add(it)
                    }
                }
            }
        }
        return map
    }

}