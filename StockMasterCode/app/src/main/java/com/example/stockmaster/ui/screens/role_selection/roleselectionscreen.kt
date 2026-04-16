package com.example.stockmaster.ui.screens.role_selection

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
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

        Spacer(modifier = Modifier.height(20.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "StockMaster",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

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

            PrimaryButton(
                text = "Tienda",
                onClick = onTiendaClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = "Cliente",
                onClick = onClienteClick
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}