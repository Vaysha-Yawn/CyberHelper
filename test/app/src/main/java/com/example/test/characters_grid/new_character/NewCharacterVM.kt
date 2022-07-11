package com.example.test.characters_grid.new_character

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewCharacterVM:ViewModel() {

    var gameName = MutableLiveData<String>()

    var gender = MutableLiveData<String>()

    val age = MutableLiveData<Int>()

    init {
        gender.value = "Мужской"
        age.value = 0
        gameName.value = ""
    }

    fun genderSetCheck(){
        gender.value = "Женский"
    }
    fun genderSetUnCheck(){
        gender.value = "Мужской"
    }

    fun plusAge() {
        age.value = age.value?.plus(1)
    }

    fun minusAge() {
        if (age.value!! > 0) {
            age.value = age.value?.minus(1)
        }
    }
}