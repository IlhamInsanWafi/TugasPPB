package com.phd.mycompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NameViewModel : ViewModel() {
    // MutableState untuk menyimpan userName
    public val _name = mutableStateOf("")
    val name: androidx.compose.runtime.State<String> = _name

    // Fungsi untuk mengubah nama
    fun setName(newName: String) {
        _name.value = newName
    }

    fun updateName(s: String) {

    }
}