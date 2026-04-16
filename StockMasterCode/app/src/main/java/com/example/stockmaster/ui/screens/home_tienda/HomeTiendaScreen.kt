package com.example.stockmaster.ui.screens.home_tienda

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

@Composable
fun HomeTiendaScreen(
    onAddProduct: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Inicio Tienda 🏪",
            fontSize = 22.sp
        )
    }
}