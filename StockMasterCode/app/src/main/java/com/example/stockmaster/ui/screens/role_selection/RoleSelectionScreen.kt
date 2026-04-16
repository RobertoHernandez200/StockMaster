package com.example.stockmaster.ui.screens.role_selection

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
fun RoleSelectionScreen(
    onClienteClick: () -> Unit,
    onTiendaClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

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

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Selecciona tu tipo de acceso",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(30.dp))

            PrimaryButton("Tienda", onTiendaClick)

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton("Cliente", onClienteClick)
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}