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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.phd.mycompose.R
import com.phd.mycompose.ui.theme.montserratFontFamily
import com.phd.mycompose.viewmodel.CurViewModel

@Composable // Fungsi utama untuk menampilkan layar seleksi mata uang
fun CurrencySelectionScreen(navController: NavController, curViewModel: CurViewModel = viewModel()) {
    val selectedCurrency by curViewModel.cur

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
            // Back button and title
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
            // Preview Selected Currency
            PreviewSelectedCurrency(selectedCurrency)
            Spacer(modifier = Modifier.height(24.dp))

            // Currency options
            CurrencyOption(
                currencyCode = "IDR",
                currencyName = "Indonesian Rupiah",
                selected = selectedCurrency == "IDR"
            ) {
                curViewModel.setCur("IDR")
            }
            CurrencyOption(
                currencyCode = "USD",
                currencyName = "American Dollar",
                selected = selectedCurrency == "USD"
            ) {
                curViewModel.setCur("USD")
            }
            CurrencyOption(
                currencyCode = "EUR",
                currencyName = "Euro",
                selected = selectedCurrency == "EUR"
            ) {
                curViewModel.setCur("EUR")
            }

            Spacer(modifier = Modifier.weight(1f))

            // Set button
            SetButtonCurrency(navController)
        }
    }
}




@Composable // Fungsi untuk menampilkan preview currency yang dipilih
fun PreviewSelectedCurrency(selectedCurrency: String) {
    val montserratFontFamily = FontFamily(
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
    ) {
        Text(
            text = "Set currency",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = montserratFontFamily,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
    Spacer(modifier = Modifier.height(30.dp))
    // Preview box for the selected currency
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF3B82F6),
                        Color(0xFF9333EA)
                    ) // Mengembalikan warna gradien ke yang sebelumnya
                ),
                shape = RoundedCornerShape(12.dp)  // Sudut bundar
            )
            .padding(16.dp)

    ) {
        Text(
            text = selectedCurrency,
            fontSize = 38.sp,
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable // Fungsi untuk menampilkan opsi mata uang
fun CurrencyOption(
    currencyCode: String,
    currencyName: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)  // Mengatur jarak vertikal antar elemen sesuai dengan mockup
            .background(
                brush = if (selected) Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF3B82F6),
                        Color(0xFF9333EA)
                    ) // Mengembalikan warna gradien ke yang sebelumnya
                ) else Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF333333),
                        Color(0xFF555555)
                    ) // Warna jika tidak terpilih
                ),
                shape = RoundedCornerShape(12.dp) // Sesuaikan sudut bundar sesuai dengan mockup
            )
            .clickable(onClick = onClick)
            .padding(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = currencyCode,
                    fontSize = 20.sp,
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = currencyName,
                    fontSize = 16.sp,
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            if (selected) {
                Text(
                    text = "✓ Selected",
                    fontSize = 16.sp,
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SetButtonCurrency(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF3B82F6), Color(0xFF9333EA))
                ),
                shape = RoundedCornerShape(12.dp)  // Sesuaikan sudut bundar agar mirip dengan desain mockup
            )
            .clickable {
                navController.navigate(
                    "fillAccount"
                )
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

@Preview(showBackground = true)
@Composable
fun CurrencySelectionScreenPreview() {
    val navController = rememberNavController()
    CurrencySelectionScreen(navController)
}

