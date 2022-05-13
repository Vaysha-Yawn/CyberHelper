package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        listIdCharacter: List<Int>
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

}