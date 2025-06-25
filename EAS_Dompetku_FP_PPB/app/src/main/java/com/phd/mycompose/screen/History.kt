package com.phd.mycompose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.phd.mycompose.R
import com.phd.mycompose.ui.theme.montserratFontFamily
import com.phd.mycompose.viewmodel.AccountViewModel
import com.phd.mycompose.viewmodel.CurViewModel
import com.phd.mycompose.viewmodel.ExpenseViewModel
import com.phd.mycompose.viewmodel.IncomeViewModel
import com.phd.mycompose.viewmodel.NameViewModel

@Composable
fun HistoryScreen(
    navController: NavController,
    accountViewModel: AccountViewModel,
    nameViewModel: NameViewModel,
    curViewModel: CurViewModel,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: "history"
    val balance by accountViewModel.balance.collectAsState()
    val currency = curViewModel.cur.value
    val incomeList by incomeViewModel.incomeList.collectAsState()
    val expenseList by expenseViewModel.expenseList.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = currentRoute,
                modifier = Modifier
            )
        },
        floatingActionButton = {
            AddButton(
                navController = navController,
                modifier = Modifier.offset(y = (56).dp)
                )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    // Greeting text
                    val name = nameViewModel.name.value
                    if (name.isNotEmpty()) {
                        Text(
                            text = "Hi $name",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = montserratFontFamily,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Total Saldo card
                    SaldoCardWithBackground(
                        backgroundColor = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFE08500), Color(0xFFFFBA56))
                        ),
                        title = "Saldo",
                        amount = balance,
                        currency = currency,
                        iconResId = R.drawable.dollar
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Daftar Income
                    Text(
                        text = "Income",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    incomeList.forEach { (title, amount) ->
                        IncomeCard(
                            backgroundColor = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF1572BE), Color(0xFF26C6DA))
                            ),
                            title = title,
                            amount = amount,
                            currency = currency,
                            iconResId = R.drawable.profits
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Daftar Expense
                    Text(
                        text = "Expense",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    expenseList.forEach { (title, amount) ->
                        ExpenseCard(
                            backgroundColor = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFAC2C29), Color(0xFFFD7676))
                            ),
                            title = title,
                            amount = amount,
                            currency = currency,
                            iconResId = R.drawable.loss
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    )
}




@Composable
fun IncomeCard(
    backgroundColor: Brush,
    title: String,
    amount: Int,
    currency: String,
    iconResId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(brush = backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(20.dp)
            .height(80.dp)  // Mengatur tinggi kartu sesuai dengan desain
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            // Bagian kiri: Icon dan Title
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                // Title
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = Color.White
                )

                // Icon
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 8.dp) // Memberikan jarak antara title dan icon
                )
            }

            // Bagian kanan: Amount dan Currency
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    text = formatAmount(amount),  // Menampilkan jumlah
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp)) // Memberikan jarak antara amount dan currency
                Text(
                    text = currency,  // Menampilkan mata uang
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ExpenseCard(
    backgroundColor: Brush,
    title: String,
    amount: Int,
    currency: String,
    iconResId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(brush = backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(20.dp)
            .height(80.dp)  // Mengatur tinggi kartu sesuai dengan desain
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            // Bagian kiri: Icon dan Title
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                // Title
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = Color.White
                )

                // Icon
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 8.dp) // Memberikan jarak antara title dan icon
                )
            }

            // Bagian kanan: Amount dan Currency
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    text = formatAmount(amount),  // Menampilkan jumlah
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp)) // Memberikan jarak antara amount dan currency
                Text(
                    text = currency,  // Menampilkan mata uang
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = Color.White
                )
            }
        }
    }
}




//@Preview
//@Composable
//
//fun PreviewHistoryScreen() {
//    val navController = rememberNavController()
//    val accountViewModel = AccountViewModel().apply {
//        updateBalance(1500)  // Contoh data balance
//    }
//    val nameViewModel = NameViewModel().apply {
//        updateName("John Doe")  // Contoh data nama
//    }
//    val curViewModel = CurViewModel().apply {
//        updateCurrency("AKL")  // Contoh data mata uang
//    }
//
//    HistoryScreen(navController = navController,
//        accountViewModel = accountViewModel,
//        nameViewModel = nameViewModel,
//        curViewModel = curViewModel)
//}

