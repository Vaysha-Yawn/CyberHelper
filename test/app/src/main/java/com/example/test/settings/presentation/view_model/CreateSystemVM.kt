package com.example.test.settings.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateSystemVM:ViewModel() {
    private var name = ""
    private val typesDamage = MutableLiveData<MutableList<String>>()
    private val groups = listOf<MutableLiveData<MutableList<String>>>(MutableLiveData(), MutableLiveData(), MutableLiveData(), MutableLiveData(), MutableLiveData())
    private val typesItems = mutableListOf<>()
}