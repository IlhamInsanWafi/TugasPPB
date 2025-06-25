package com.phd.mycompose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class userName : ViewModel() {

    // MutableState untuk menyimpan nama
    private val _name = mutableStateOf("")
    val name: State<String> = _name

    fun setName(newName: String) {
        _name.value = newName
    }
}