package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data_base.Goal
import com.example.test.widgets.Roll

class FewRollVM : ViewModel() {

    val fragments = MutableLiveData(mutableMapOf<Int, Roll>())

    val chosenRolls = mutableMapOf<Int, OneRoll>()

    val listId = MutableLiveData<MutableList<Int>>()

    var keyFragment = 0

    val allGoals = mutableListOf<Goal>()

    fun setAllGoals(mCharacterVM: CharacterDAO) {

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

    init {
        listId.value = mutableListOf()
    }

    fun add(id: Int, fragment: Roll) {
        listId.value?.add(id)
        fragments.value!![id] = fragment

    }

    fun delete(id: Int, position: Int) {
        listId.value?.remove(id)
        fragments.value!!.remove(id)

    }

    fun getIndex(element: Int): Int {
        return listId.value?.indexOf(element) ?: 0
    }

    fun getElement(index: Int): Int {
        return listId.value?.get(index) ?: 0
    }
}