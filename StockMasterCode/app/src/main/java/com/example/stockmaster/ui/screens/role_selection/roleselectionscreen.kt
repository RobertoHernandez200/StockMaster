package com.example.stockmaster.ui.screens.role_selection

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
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
        verticalArrangement = Arrangement.Center
    ) {

        PrimaryButton(
            text = "Cliente",
            onClick = onClienteClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            text = "Tienda",
            onClick = onTiendaClick
        )
    }
}