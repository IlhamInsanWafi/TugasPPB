package AccountViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.phd.mycompose.viewmodel.AccountViewModel
import com.phd.mycompose.viewmodel.ExpenseViewModel
import com.phd.mycompose.viewmodel.IncomeViewModel

class AccountViewModelFactory(
    private val incomeViewModel: IncomeViewModel,
    private val expenseViewModel: ExpenseViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(incomeViewModel, expenseViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

