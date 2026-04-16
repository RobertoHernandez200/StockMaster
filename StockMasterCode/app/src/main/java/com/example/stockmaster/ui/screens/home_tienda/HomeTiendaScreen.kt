package com.example.stockmaster.ui.screens.home_tienda

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment

@Composable
fun HomeTiendaScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hola Tienda 🏪",
            fontSize = 24.sp
        )
    }
}