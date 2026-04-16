package com.example.stockmaster.ui.screens.home_cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment

@Composable
fun HomeClienteScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hola Cliente 👋",
            fontSize = 24.sp
        )
    }
}