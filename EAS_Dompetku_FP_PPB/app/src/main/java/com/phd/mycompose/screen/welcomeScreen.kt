package com.phd.mycompose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phd.mycompose.R

@Composable
fun FinancialAppScreen1(navController: NavController) {
    // Konten UI untuk FinancialAppScreen1 seperti pada contoh sebelumnya
        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Gambar utama
        Image(
            painter = painterResource(id = R.drawable.money_bag_1), // Ganti dengan resource gambar Anda
            contentDescription = null,
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Teks Judul
        Text(
            text = "Menabung pangkal Kaya",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Teks Deskripsi
        Text(
            text = "Pengelolaan keuangan yang efektif merupakan fondasi penting bagi stabilitas dan kesejahteraan finansial individu.",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol Panah
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xFF333333)) // Warna background tombol
                .clickable {
                    // Aksi saat tombol diklik
                    // Contoh: menggunakan Navigation untuk berpindah antar layar
                    navController.navigate("FinancialAppScreen2")
                }
                .indication(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple()
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.button_right), // Ganti dengan resource panah kanan
                contentDescription = "Next",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun FinancialAppScreen2(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Gambar utama
        Image(
            painter = painterResource(id = R.drawable.stability), // Ganti dengan resource gambar Anda
            contentDescription = null,
            modifier = Modifier.size(200.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Teks Judul
        Text(
            text = "Persiapkan masa depan anda sekarang!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )

        // Teks Deskripsi
        Text(
            text = "Kemapanan seseorang bisa dilihat dengan indikator finansialnya. Aplikasi ini membantu untuk mewujudkan masa depan lebih baik.",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol Panah
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xFF333333)) // Warna background tombol
                .clickable {
                    // Aksi saat tombol diklik
                    navController.navigate("startingApp")
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.button_right), // Ganti dengan resource panah kanan
                contentDescription = "Next",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
