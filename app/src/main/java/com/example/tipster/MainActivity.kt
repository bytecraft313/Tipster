package com.example.tipster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tipster.ui.theme.TipsterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipsterTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipsterApp()
                }
            }
        }
    }
}

@Composable
fun TipsterApp() {
    var billAmount by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf("") }
    var tipAmount by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = billAmount,
            onValueChange = { billAmount = it },
            label = { Text("Enter bill amount") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = tipPercent,
            onValueChange = { tipPercent = it },
            label = { Text("Enter tip %") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val bill = billAmount.toDoubleOrNull()
                val tip = tipPercent.toDoubleOrNull()

                tipAmount = if (bill != null && tip != null) {
                    val calculatedTip = bill * tip / 100
                    focusManager.clearFocus()
                    "Tip amount: $%.2f".format(calculatedTip)
                } else {
                    "Please enter valid numbers"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Tip")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = tipAmount ?: "Tip amount will be shown here",
            style = MaterialTheme.typography.titleMedium
        )
    }
}
