package com.example.stockmaster.ui.screens.lista_deseos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.stockmaster.ui.components.LineTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.navigation.NavController

@Composable
fun CrearListaScreen(navController: NavController) {

    var nombreLista by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(20.dp)
    ) {

        // 🔙 HEADER CON FLECHA
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "Agregar lista de deseos",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 📝 INPUT
        LineTextField(
            value = nombreLista,
            onValueChange = {
                nombreLista = it
            },
            label = "Nombre de lista"
        )

        Spacer(modifier = Modifier.weight(1f))

        // BOTÓN
        Button(
            onClick = {
                if (nombreLista.isNotBlank()) {
                    navController.navigate("seleccionar_tienda/$nombreLista")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .offset(y = 10.dp)
                .height(55.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6A5AE0)
            )
        ) {
            Text("Avanzar")
        }
    }
}