
import AccountViewModelFactory.AccountViewModelFactory
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.phd.mycompose.R
import com.phd.mycompose.screen.CategoryDialog
import com.phd.mycompose.screen.CurrencySelectionScreen
import com.phd.mycompose.screen.DashboardScreen
import com.phd.mycompose.screen.FillAccountScreen
import com.phd.mycompose.screen.HistoryScreen
import com.phd.mycompose.screen.SetExpenseDialog
import com.phd.mycompose.viewmodel.AccountViewModel
import com.phd.mycompose.viewmodel.CurViewModel
import com.phd.mycompose.viewmodel.ExpenseViewModel
import com.phd.mycompose.viewmodel.IncomeViewModel
import com.phd.mycompose.viewmodel.NameViewModel


@Composable
fun StartingApp() {
    val navController = rememberNavController()
    val nameViewModel: NameViewModel = viewModel()
    val incomeViewModel: IncomeViewModel = viewModel()
    val expenseViewModel: ExpenseViewModel = viewModel()
    val accountViewModel: AccountViewModel = viewModel(factory = AccountViewModelFactory(incomeViewModel, expenseViewModel))
    val curViewModel: CurViewModel = viewModel()


    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        //PENTING JANGAN LUPA !!!!!
        NavHost(navController = navController, startDestination = "setName") {
            composable("setName") {
                InputNameScreen(navController, nameViewModel)
            }
            composable("displayName") {
                DisplayName(navController, nameViewModel)
            }
            composable("setCurrency") {
                CurrencySelectionScreen(navController, curViewModel)
            }
            composable("fillAccount") {
                FillAccountScreen(navController, accountViewModel)
            }
            composable("dashboard") {
                DashboardScreen(navController, accountViewModel, nameViewModel, curViewModel, incomeViewModel, expenseViewModel)
            }
            composable("history") {
                HistoryScreen(navController, accountViewModel, nameViewModel, curViewModel, incomeViewModel, expenseViewModel)
            }

            composable("catDialog") {
                CategoryDialog(
                    onDismiss = {
                        navController.popBackStack() // Menutup dialog dengan cara kembali ke rute sebelumnya
                    },
                    navController = navController
                )
            }
            composable("setIncome") {
                SetIncomeDialog(
                    onDismiss = { navController.popBackStack() },
                    incomeViewModel = incomeViewModel
                )
            }
            composable("setExpense") {
                SetExpenseDialog(
                    onDismiss = { navController.popBackStack() },
                    expenseViewModel = expenseViewModel
                )
            }
        }
    }
}




@Composable
fun InputNameScreen(navController: NavController, nameViewModel: NameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Header(navController)
        Spacer(modifier = Modifier.height(50.dp))
        NameInput(nameViewModel)
        Spacer(modifier = Modifier.weight(1f)) // Menambahkan spacer agar tombol berada di bagian bawah
        SetButtonName(nameViewModel, navController)
    }
}

@Composable
fun Header(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.button_left), // Sesuaikan dengan resource icon Anda
            contentDescription = "Back",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xFF333333)) // Background hitam dengan bentuk lingkaran
                .clickable { /* Aksi kembali */
                    navController.popBackStack()
                },
        )
    }
}

@Composable
fun NameInput(nameViewModel: NameViewModel) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
    ) {
        Text(
            text = "What's your name?",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        val userName = remember { mutableStateOf(TextFieldValue("")) }

        BasicTextField(
            value = userName.value,
            onValueChange = {
                userName.value = it
                nameViewModel.setName(it.text)
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.small)
                .padding(horizontal = 16.dp, vertical = 15.dp)
        ) {
            if (userName.value.text.isEmpty()) {
                Text(
                    text = "Your name",
                    fontSize = 18.sp,
                    style = TextStyle(color = Color.Gray)
                )
            }
            it()
        }
    }
}

@Composable
fun SetButtonName(nameViewModel: NameViewModel, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF3B82F6), Color(0xFF9333EA))
                ),
                shape = MaterialTheme.shapes.medium
            )
            .clickable {
                val enteredName = nameViewModel.name.value
                if (enteredName.isNotEmpty()) {
                    navController.navigate("displayName")
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "SET",
            fontSize = 20.sp,
            style = TextStyle(color = Color.White),
            fontWeight = FontWeight.Bold
        )
    }
}



//@Preview(showBackground = true)
//@Composable
//fun NamePreview() {
//    StartingApp(navController: NavController )
//    val navController = rememberNavController()
//}
