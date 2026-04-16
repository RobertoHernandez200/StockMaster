package com.example.stockmaster.ui.screens.home_tienda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.example.stockmaster.ui.components.PrimaryButton

@Composable
fun HomeTiendaScreen(
    onAddProduct: () -> Unit
) {

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF6A5AE0), Color(0xFF8E7CFF))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp)
    ) {

        Text(
            text = "Inicio",
            color = Color.White,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        CardItem("Productos", "Añadir producto", onAddProduct)
        Spacer(modifier = Modifier.height(16.dp))

        CardItem("Usuarios", "Agregar usuario") {}
        Spacer(modifier = Modifier.height(16.dp))

        CardItem("Proveedores", "Agregar contacto") {}
    }
}

@Composable
fun CardItem(title: String, buttonText: String, onClick: () -> Unit) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(text = title, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(10.dp))

            PrimaryButton(
                text = buttonText,
                onClick = onClick
            )
        }
    }
}