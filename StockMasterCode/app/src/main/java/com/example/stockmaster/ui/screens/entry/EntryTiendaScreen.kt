package com.example.stockmaster.ui.screens.entry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EntryTiendaScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onBack: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // 🔙 Botón atrás
            TextButton(onClick = onBack) {
                Text("←", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 🔷 LOGO (puedes cambiar por Image después)
                Text(
                    text = "StockMaster",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3F51B5)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Gestiona tu inventario fácil y rápido",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(60.dp))

                //  BOTÓN INGRESAR
                Button(
                    onClick = onLoginClick,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6A5AE0)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    Text("Ingresar", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                //  BOTÓN REGISTRARSE
                Button(
                    onClick = onRegisterClick,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6A5AE0)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    Text("Registrarse", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🔹 Texto legal abajo
            Text(
                text = "Al crear una cuenta, aceptas los Términos de Uso y la Política de Privacidad",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}