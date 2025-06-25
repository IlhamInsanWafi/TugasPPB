package com.phd.mycompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phd.mycompose.ui.theme.MyComposeTheme
import kotlinx.coroutines.delay

class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Splash {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                        finish() // Menutup SplashScreenActivity agar tidak kembali ke splash screen saat menekan tombol back
                    }
//
                }
            }
        }
    }
}

@Composable
fun Splash(onTimeout: () -> Unit) {
    val imageLogo: Painter = painterResource(id = R.drawable.purse)

    // Definisikan FontFamily dengan berbagai bobot font Montserrat
    val montserratFontFamily = FontFamily(
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = imageLogo,
                contentDescription = "Logo",
                modifier = Modifier.size(140.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Dompetku",
                fontSize = 30.sp,
                style = TextStyle(
                    fontFamily = montserratFontFamily, // Menggunakan font Montserrat
                    fontWeight = FontWeight.Bold // Mengatur bobot font
                )
            )
        }
    }
    LaunchedEffect(Unit) {
        delay(2500) // tunggu 3 detik
        onTimeout()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {  // Ubah nama fungsi preview di sini
    Splash {}
}
