package com.example.stockmaster.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.stockmaster.ui.theme.BackgroundColor
import com.example.stockmaster.ui.theme.LightGrayText
import com.example.stockmaster.ui.theme.Poppins

@Composable
fun SplashScreen(
    onStartClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        // LOGO + TITULO
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

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
        }



        Text(
            text = "¡Bienvenido a StockMaster!",
            fontFamily = Poppins,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = "Empezar...",
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}