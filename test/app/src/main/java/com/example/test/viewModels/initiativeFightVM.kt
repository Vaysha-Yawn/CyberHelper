package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data_base.InitiativeFight
import io.realm.RealmList

class initiativeFightVM : ViewModel() {

    private val DAO = InitiativeFightDAO()

    val fightList = MutableLiveData<RealmList<InitiativeFight>>(RealmList<InitiativeFight>())

    fun addInitiativeFight(
        id: Int,
        nameFight: String,
        listIdCharacter: List<Int>
    ) {
        val initiativeFight = DAO.addInitiativeFight(
            id,
            nameFight,
            listIdCharacter
        )
        fightList.value!!.add(initiativeFight)
    }
}