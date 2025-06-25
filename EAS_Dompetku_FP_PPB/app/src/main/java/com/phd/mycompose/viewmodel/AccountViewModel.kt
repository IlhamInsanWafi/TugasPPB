package com.phd.mycompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AccountViewModel(
    private val incomeViewModel: IncomeViewModel,
    private val expenseViewModel: ExpenseViewModel
) : ViewModel() {

    // Menggunakan StateFlow untuk menyimpan balance
    private val _balance = MutableStateFlow(0)
    val balance: StateFlow<Int> = _balance

    // Variabel untuk menyimpan balance awal
    private var initialBalance = 0

    init {
        // Mengupdate balance ketika income atau expense berubah
        viewModelScope.launch {
            combine(
                incomeViewModel.incomeTotal,
                expenseViewModel.expenseTotal
            ) { incomeTotal, expenseTotal ->
                initialBalance + incomeTotal - expenseTotal  // Menghitung balance sebagai initialBalance + income - expense
            }.collect { newBalance ->
                _balance.value = newBalance
            }
        }
    }

    // Fungsi untuk memperbarui saldo awal
    fun updateBalance(newBalance: Int) {
        initialBalance = newBalance
        // Perbarui balance berdasarkan initialBalance dan total income/expense yang terbaru
        _balance.value = initialBalance + incomeViewModel.incomeTotal.value - expenseViewModel.expenseTotal.value
    }
}
