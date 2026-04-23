package com.example.stockmaster.ui.screens.role_selection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmaster.R
import com.example.stockmaster.ui.components.PrimaryButton
import com.example.stockmaster.ui.theme.BackgroundColor
import com.example.stockmaster.ui.theme.LightGrayText
import com.example.stockmaster.ui.theme.Poppins
import com.example.stockmaster.ui.theme.PurpleEnd
import com.example.stockmaster.ui.theme.PurpleStart

@Composable
fun RoleSelectionScreen(
    onClienteClick: () -> Unit,
    onTiendaClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔷 TÍTULO CON DEGRADADO
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

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Selecciona tu tipo de acceso",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Poppins
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 🔘 BOTONES (usan tu estilo global)
            PrimaryButton(
                text = "Tienda",
                onClick = onTiendaClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = "Cliente",
                onClick = onClienteClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )
        }
    }
}