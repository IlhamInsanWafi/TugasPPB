package com.example.kalkulator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kalkulator.ui.theme.KalkulatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    KalkulatorUI()
                }
            }
        }
    }
}

@Composable
fun KalkulatorUI() {
    val context = LocalContext.current

    var num1 by remember { mutableStateOf("0") }
    var num2 by remember { mutableStateOf("0") }

    var suhu by remember { mutableStateOf("0") }
    var hasilKonversi by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Kalkulator
        Text(text = "Kalkulator Sederhana", style = MaterialTheme.typography.titleMedium)
        TextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Angka pertama") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Angka kedua") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                val result = num1.toIntOrNull()?.plus(num2.toIntOrNull() ?: 0)
                Toast.makeText(context, "Hasil: $result", Toast.LENGTH_SHORT).show()
            }) {
                Text("Tambah")
            }
            Button(onClick = {
                val result = num1.toIntOrNull()?.minus(num2.toIntOrNull() ?: 0)
                Toast.makeText(context, "Hasil: $result", Toast.LENGTH_SHORT).show()
            }) {
                Text("Kurang")
            }
            Button(onClick = {
                val result = num1.toIntOrNull()?.times(num2.toIntOrNull() ?: 0)
                Toast.makeText(context, "Hasil: $result", Toast.LENGTH_SHORT).show()
            }) {
                Text("Kali")
            }
            Button(onClick = {
                val result = if ((num2.toIntOrNull() ?: 0) != 0)
                    num1.toIntOrNull()?.div(num2.toIntOrNull() ?: 1)
                else "Tidak bisa dibagi 0"
                Toast.makeText(context, "Hasil: $result", Toast.LENGTH_SHORT).show()
            }) {
                Text("Bagi")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Konversi Suhu
        Text(text = "Konversi Suhu", style = MaterialTheme.typography.titleMedium)
        TextField(
            value = suhu,
            onValueChange = { suhu = it },
            label = { Text("Masukkan suhu") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                val c = suhu.toFloatOrNull()
                hasilKonversi = if (c != null) {
                    val f = (c * 9 / 5) + 32
                    "$c °C = $f °F"
                } else {
                    "Input tidak valid"
                }
            }) {
                Text("C → F")
            }

            Button(onClick = {
                val f = suhu.toFloatOrNull()
                hasilKonversi = if (f != null) {
                    val c = (f - 32) * 5 / 9
                    "$f °F = $c °C"
                } else {
                    "Input tidak valid"
                }
            }) {
                Text("F → C")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = hasilKonversi)
    }
}

@Preview(showBackground = true)
@Composable
fun KalkulatorPreview() {
    KalkulatorTheme {
        KalkulatorUI()
    }
}
