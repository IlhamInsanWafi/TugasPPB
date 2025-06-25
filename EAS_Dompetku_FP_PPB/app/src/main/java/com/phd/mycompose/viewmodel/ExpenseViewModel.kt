package com.phd.mycompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ExpenseViewModel : ViewModel() {

    // State untuk menyimpan daftar expense yang sudah di-set
    private val _expenseList = MutableStateFlow<List<Pair<String, Int>>>(emptyList())
    val expenseList: StateFlow<List<Pair<String, Int>>> get() = _expenseList

    // State untuk menyimpan total expense
    val expenseTotal: StateFlow<Int> = _expenseList
        .map { expenseList ->
            expenseList.sumOf { it.second }  // Menghitung total amount
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // Fungsi untuk menambahkan expense baru
    fun addExpense(title: String, amount: Int) {
        _expenseList.update { currentList ->
            currentList + Pair(title, amount)
        }
    }
}
