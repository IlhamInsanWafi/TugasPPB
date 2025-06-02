package com.example.kalkulatorkonversi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kalkulatorkonversi.ui.theme.KalkulatorKonversiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorKonversiTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CurrencyConverterUI()
                }
            }
        }
    }
}

@Composable
fun CurrencyConverterUI() {
    val currencyOptions = listOf("IDR", "USD", "EUR")
    var amountInput by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("IDR") }
    var toCurrency by remember { mutableStateOf("USD") }

    val exchangeRatesToIDR = mapOf(
        "IDR" to 1.0,
        "USD" to 15200.0,
        "EUR" to 16500.0
    )

    val convertedAmount = remember(amountInput, fromCurrency, toCurrency) {
        val input = amountInput.toDoubleOrNull() ?: 0.0
        val fromRate = exchangeRatesToIDR[fromCurrency] ?: 1.0
        val toRate = exchangeRatesToIDR[toCurrency] ?: 1.0
        val idrValue = input * fromRate
        idrValue / toRate
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text("Kalkulator Konversi Mata Uang", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Masukkan jumlah uang") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            CurrencyDropdown(label = "Dari", selected = fromCurrency, options = currencyOptions) {
                fromCurrency = it
            }

            CurrencyDropdown(label = "Ke", selected = toCurrency, options = currencyOptions) {
                toCurrency = it
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hasil Konversi: %.4f $toCurrency".format(convertedAmount),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun CurrencyDropdown(label: String, selected: String, options: List<String>, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.width(150.dp)) {
        Text(text = label)
        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(selected)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyConverterPreview() {
    KalkulatorKonversiTheme {
        CurrencyConverterUI()
    }
}
