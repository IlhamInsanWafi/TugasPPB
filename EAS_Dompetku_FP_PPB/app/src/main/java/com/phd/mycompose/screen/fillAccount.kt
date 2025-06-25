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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.phd.mycompose.R
import com.phd.mycompose.ui.theme.montserratFontFamily
import com.phd.mycompose.viewmodel.AccountViewModel

@Composable
fun FillAccountScreen(navController: NavController, accountViewModel: AccountViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            // Back button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Back icon
                Image(
                    painter = painterResource(id = R.drawable.button_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )

                Spacer(modifier = Modifier.width(16.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Spacer(modifier = Modifier.width(16.dp))
            FillAccount()
            // Add account button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF3B82F6),
                                Color(0xFF9333EA)
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
                    .clickable {
                        showDialog = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (showDialog) {
                AddAccountDialog(
                    onDismiss = { showDialog = false },
                    accountViewModel = accountViewModel,
                    navController = navController)

            }
        }
    }
}
@Composable
fun AddAccountDialog(onDismiss: () -> Unit, accountViewModel: AccountViewModel, navController: NavController ) {
    var balance by remember { mutableStateOf(0) }
    var balanceInput by remember { mutableStateOf("0") }
    androidx.compose.ui.window.Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                // Add account title
                Text(
                    text = "Fill account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))

                // Account balance input (placeholder only for now)

                    Text(
                        text = "Enter Account Balance",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colorScheme.onSurface
                    )


                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = balanceInput,
                    onValueChange = {
                        balanceInput = it
                        balance = it.toIntOrNull() ?: 0 // Mengonversi ke Int atau 0 jika input tidak valid
                        accountViewModel.updateBalance(balance) // Menyimpan di BalanceViewModel
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFontFamily,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Next button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF1976D2), Color(0xFF8E24AA))
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            val enteredBalance = accountViewModel.balance.value
                            if (enteredBalance > 0) {
                                // Navigasi ke UI selanjutnya
                                navController.navigate("dashboard")
                            }
                            // Perform next action
                            onDismiss()  // Dismiss the dialog
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "NEXT",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFontFamily,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun FillAccount() {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
    ) {
        Text(
            text = "Add account",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = montserratFontFamily,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
    Spacer(modifier = Modifier.height(30.dp))
    }


@Preview(showBackground = true)
@Composable
fun AddAccountPreview() {
    val navController = rememberNavController()
    FillAccountScreen(navController)
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewAddAccountDialog() {
//    // Membuat instance palsu dari ViewModel dan NavController untuk keperluan preview
//    val fakeAccountViewModel = AccountViewModel()
//    val fakeNavController = rememberNavController()
//
//    // Memanggil fungsi AddAccountDialog dengan parameter palsu
//    AddAccountDialog(
//        onDismiss = {},
//        accountViewModel = fakeAccountViewModel,
//        navController = fakeNavController
//    )
//}
