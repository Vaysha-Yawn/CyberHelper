package com.example.test.components.few_roll

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.components.few_roll.roll.Roll
import com.example.test.data_base.realm.other_realm_object.Goal
import com.example.test.data_base.realm.character.CharacterVM
import com.example.test.viewModels.OneRoll

class FewRollVM : ViewModel() {

    private var lastId = 0

    fun getNewId():Int{
        lastId+=1
        return lastId
    }

    val fragments = MutableLiveData(mutableMapOf<Int, Roll>())

    val chosenRolls = mutableMapOf<Int, OneRoll>()

    val listId = MutableLiveData<MutableList<Int>>(mutableListOf())

    val allGoals = mutableListOf<Goal>()

    fun setAllGoals(mCharacterVM: CharacterVM) {
        mCharacterVM.characterList.value?.forEach {
            if (it.gameId == mCharacterVM.gameId) {
                if (it.id != mCharacterVM.characterId) {
                    val goal = Goal()
                    goal.characterId = it.id
                    val name = it.attributes.singleOrNull { gp ->
                        gp.title == "Базовые параметры"
                    }?.attributes?.listParamStr?.singleOrNull { pn ->
                        pn.name == "Имя персонажа"
                    }?.value ?: ""
                    goal.name = name
                    allGoals.add(goal)
                }
            }
        }
    }

    fun add(id: Int, fragment: Roll) {
        listId.value?.add(id)
        fragments.value!![id] = fragment
        chosenRolls[id] = OneRoll()
    }

    fun delete(id: Int) {
        listId.value?.remove(id)
        fragments.value!!.remove(id)
        chosenRolls.remove(id)
    }

    fun getIndex(element: Int): Int {
        return listId.value?.indexOf(element) ?: 0
    }

    fun getElement(index: Int): Int {
        return listId.value?.get(index) ?: 0
    }
}