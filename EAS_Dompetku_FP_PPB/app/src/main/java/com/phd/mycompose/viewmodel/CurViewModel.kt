package com.phd.mycompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CurViewModel : ViewModel() {
    val _cur = mutableStateOf("")
    val cur: androidx.compose.runtime.State<String> = _cur

    // Fungsi untuk mengubah nama
    fun setCur(newCur: String) {
        _cur.value = newCur
    }

    fun updateCurrency(s: String) {

    }
}