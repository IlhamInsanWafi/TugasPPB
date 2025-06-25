package com.phd.mycompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class IncomeViewModel : ViewModel() {

    // State untuk menyimpan daftar income yang sudah di-set
    private val _incomeList = MutableStateFlow<List<Pair<String, Int>>>(emptyList())
    val incomeList: StateFlow<List<Pair<String, Int>>> get() = _incomeList

    // State untuk menyimpan total income
    val incomeTotal: StateFlow<Int> = _incomeList
        .map { incomeList ->
            incomeList.sumOf { it.second }  // Menghitung total amount
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // Fungsi untuk menambahkan income baru
    fun addIncome(title: String, amount: Int) {
        _incomeList.update { currentList ->
            currentList + Pair(title, amount)
        }
    }
}



