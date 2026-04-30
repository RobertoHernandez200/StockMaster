package com.example.stockmaster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuDrawer(
    onInicio: () -> Unit,
    onTiendas: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(Color(0xFF4B3FB4))
            .padding(20.dp)
    ) {

        Text("Menú", color = Color.White, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(30.dp))

        MenuItem("Inicio", onInicio)
        MenuItem("Tiendas", onTiendas)
        MenuItem("Listas de deseos", {})
        MenuItem("Opciones", {})
    }
}

@Composable
fun MenuItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    )
}