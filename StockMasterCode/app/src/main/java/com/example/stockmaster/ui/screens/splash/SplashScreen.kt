package com.example.stockmaster.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.stockmaster.R
import com.example.stockmaster.ui.components.PrimaryButton

@Composable
fun SplashScreen(
    onStartClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "StockMaster",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Gestiona tu inventario fácil y rápido",
                fontSize = 14.sp
            )
        }

        Text(
            text = "¡Bienvenido a StockMaster!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        PrimaryButton(
            text = "Empezar...",
            onClick = onStartClick
        )
    }
}