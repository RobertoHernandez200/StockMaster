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
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeTiendaScreen(
    onAddProduct: () -> Unit,
    onLogout: () -> Unit
) {

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF6A5AE0), Color(0xFF8E7CFF))
    )

    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp)
    ) {

        // 🔥 HEADER con botón salir
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Inicio",
                color = Color.White,
                fontSize = 22.sp
            )

            TextButton(
                onClick = {
                    auth.signOut()   // 🔥 cerrar sesión
                    onLogout()      // 🔥 navegar al login
                }
            ) {
                Text("Salir", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        CardBox("Productos", "Añadir producto", onAddProduct)
        Spacer(modifier = Modifier.height(16.dp))

        CardBox("Usuarios", "Agregar usuario") {}
        Spacer(modifier = Modifier.height(16.dp))

        CardBox("Proveedores", "Agregar contacto") {}
    }
}

@Composable
fun CardBox(
    title: String,
    buttonText: String,
    onClick: () -> Unit
) {

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            PrimaryButton(
                text = buttonText,
                onClick = onClick,
            )
        }
    }
}