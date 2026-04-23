package com.example.stockmaster.ui.screens.entry

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmaster.R
import com.example.stockmaster.ui.components.PrimaryButton
import com.example.stockmaster.ui.theme.LightGrayText
import com.example.stockmaster.ui.theme.Poppins

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

            // 🔹 HEADER
            TextButton(onClick = onBack) {
                Text("←", fontSize = 20.sp)
            }

            // 🔹 CONTENIDO CENTRADO
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // ocupa espacio y permite centrar
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Gestiona tu inventario fácil y rápido",
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    color = LightGrayText
                )

                Spacer(modifier = Modifier.height(50.dp))

                // INGRESAR
                PrimaryButton(
                    text = "Ingresar",
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                //Registrarse
                PrimaryButton(
                    text = "Registrarse",
                    onClick = onRegisterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // FOOTER
            Text(
                text = "Al crear una cuenta, aceptas los Términos de Uso y la Política de Privacidad",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}