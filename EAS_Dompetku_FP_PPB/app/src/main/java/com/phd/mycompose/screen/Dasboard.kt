package com.phd.mycompose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun DashboardScreen(
    navController: NavController,
    accountViewModel: AccountViewModel,
    nameViewModel: NameViewModel,
    curViewModel: CurViewModel,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: "dashboard"
    val balance by accountViewModel.balance.collectAsState() // Menggunakan `collectAsState` untuk mengakses balance sebagai State
    val currency = curViewModel.cur.value
    val income by incomeViewModel.incomeTotal.collectAsState()
    val expense by expenseViewModel.expenseTotal.collectAsState()
//    val incomeViewModel: IncomeViewModel = viewModel()
//    val expenseViewModel: ExpenseViewModel = viewModel()


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                }

                // Total Saldo card
                SaldoCardWithBackground(
                    backgroundColor = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFE08500), Color(0xFFFFBA56))
                    ),
                    title = "Saldo",
                    amount = balance, // Menampilkan balance yang diambil dari ViewModel
                    currency = currency,
                    iconResId = R.drawable.dollar // Ganti dengan ikon yang sesuai
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Income card
                DashboardCard(
                    backgroundColor = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF1572BE), Color(0xFF26C6DA))
                    ),
                    title = "Income",
                    amount = income,
                    currency = currency,
                    iconResId = R.drawable.profits // Ganti dengan ikon yang sesuai
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Expense card
                DashboardCard(
                    backgroundColor = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFAC2C29), Color(0xFFFD7676))
                    ),
                    title = "Expense",
                    amount = expense,
                    currency = currency,
                    iconResId = R.drawable.loss // Ganti dengan ikon yang sesuai
                )
            }

            // AddButton positioned in the center of the BottomNavigationBar
            // Bottom Navigation Bar
            BottomNavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                navController = navController,
                currentRoute = currentRoute
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp) // Adjust padding as needed to position the button above the navbar
            ) {
                AddButton(navController = navController) // Mengirimkan `navController` dengan benar
            }
        }
    }
}




@Composable
fun CategoryDialog(onDismiss: () -> Unit, navController: NavController) {
    androidx.compose.ui.window.Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Select Category",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Income Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(60.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF1572BE), Color(0xFF26C6DA))
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            navController.navigate("setIncome")
                            onDismiss()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Income",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFontFamily,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Expense Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(60.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFAC2C29), Color(0xFFFD7676))
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            navController.navigate("setExpense")
                            onDismiss()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Expense",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFontFamily,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
    }
}




@Composable
fun AddButton(modifier: Modifier = Modifier, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            showDialog = true
        },
        modifier = modifier
            .size(70.dp) // Adjust size as needed
            .background(Color.Transparent, shape = RoundedCornerShape(35.dp)) // Transparent background
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = "Add",
            modifier = Modifier.size(100.dp),
            tint = Color.Unspecified // Use original icon color
        )
    }

    if (showDialog) {
        CategoryDialog(
            onDismiss = { showDialog = false },
            navController = navController)
    }
}






@Composable
fun DashboardCard(
    backgroundColor: Brush,
    title: String,
    amount: Int,
    currency: String,
    iconResId: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()

        ) {
            Column {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = Color.White
                )

                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = formatAmount(amount),  // Konversi amount dari Int ke String
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp)) // Memberikan jarak antara jumlah dan mata uang
                Text(
                    text = currency,
                    fontSize = 28.sp,
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun SaldoCardWithBackground(
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
            .padding(0.dp)
            .height(150.dp)  // Mengatur tinggi kartu
    ) {
        // Background image
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize() // Mengisi seluruh ukuran box
                .clip(RoundedCornerShape(0.dp)), // Sesuaikan bentuk gambar dengan Box
            contentScale = ContentScale.Crop, // Atur skala agar gambar sesuai dengan Box
            alpha = 0.18f // Mengatur transparansi gambar agar tidak terlalu dominan
        )

        // Content di atas gambar background
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Mengatur padding di dalam box
        ) {
            // Title di bagian atas
            Text(
                text = title,
                fontSize = 28.sp, // Ukuran font yang lebih kecil untuk title
                fontWeight = FontWeight.Bold,
                fontFamily = montserratFontFamily,
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.weight(1f)) // Membuat jarak antara title dan amount

            // Amount dan currency di bagian bawah
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.align(Alignment.End) // Menyelaraskan teks amount dan currency di kanan bawah
            ) {
                Text(
                    text = formatAmount(amount),  // Konversi amount dari Int ke String
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp)) // Memberikan jarak antara jumlah dan mata uang
                Text(
                    text = currency,
                    fontSize = 36.sp,
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}




@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentRoute: String // Menambahkan parameter untuk menentukan route yang aktif
) {

    NavigationBar(
        modifier = modifier
            .fillMaxWidth(),
        containerColor = Color(0xFF004F8D),
        tonalElevation = 5.dp
    ) {
        // Home Button
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    modifier = Modifier.size(44.dp),
                    tint = Color.Unspecified
                )
            },
            selected = currentRoute == "dashboard", // Menandai item yang aktif
            onClick = { navController.navigate("dashboard") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFF0060AA),
                selectedIconColor = Color.Unspecified, // Menggunakan warna asli ikon
                unselectedIconColor = Color.Unspecified // Menggunakan warna asli ikon
            )
        )

        // Spacer to leave space for AddButton
        Spacer(modifier = Modifier.width(60.dp))

        // Transaction Button
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.trans),
                    contentDescription = "Transaction",
                    modifier = Modifier.size(47.dp),
                    tint = Color.Unspecified
                )
            },
            selected = currentRoute == "history", // Menandai item yang aktif
            onClick = { navController.navigate("history") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFF0060AA),
                selectedIconColor = Color.Unspecified, // Menggunakan warna asli ikon
                unselectedIconColor = Color.Unspecified // Menggunakan warna asli ikon
            )
        )
    }
}



fun formatAmount(amount: Int): String {
    return String.format("%,d", amount).replace(",", ".")
}




//@Preview(showBackground = true)
//@Composable
//fun PreviewDashboardScreen() {
//    val navController = rememberNavController()
//    val accountViewModel = AccountViewModel().apply {
//        updateBalance(1500)  // Contoh data balance
//    }
//    val nameViewModel = NameViewModel().apply {
//        updateName("John Doe")  // Contoh data nama
//    }
//    val curViewModel = CurViewModel().apply {
//        updateCurrency("USD")  // Contoh data mata uang
//    }
//
//    DashboardScreen(
//        navController = navController,
//        accountViewModel = accountViewModel,
//        nameViewModel = nameViewModel,
//        curViewModel = curViewModel,
//        incomeViewModel = IncomeViewModel()
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewCategoryDialog() {
//    CategoryDialog(
//        onDismiss = {},
//        navController = rememberNavController()
//    )
//}






