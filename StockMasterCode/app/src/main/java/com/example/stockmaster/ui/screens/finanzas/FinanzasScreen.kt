package com.example.stockmaster.ui.screens.finanzas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Definición de colores basados en la imagen image_a2fa0c.png
val PrincipalPurple = Color(0xFF6C63FF)
val LightPurple = Color(0xFFB4B0FF)
val TextPurple = Color(0xFF5E5CE6)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanzasScreen(
    navController: NavController,
    onBack: () -> Unit
) {
    var selectedTime by remember { mutableStateOf("Hoy") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finanzas", color = TextPurple, fontSize = 28.sp) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = PrincipalPurple
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- 1. SELECTOR DE TIEMPO (CHIPS) ---
            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TimeChip("Hoy", selectedTime == "Hoy") { selectedTime = "Hoy" }
                TimeChip("Semana", selectedTime == "Semana") { selectedTime = "Semana" }
                TimeChip("Mes", selectedTime == "Mes") { selectedTime = "Mes" }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- 2. GRID DE RESUMEN (CENTRAL) ---
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                // Fila Superior
                Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                    SummaryItem("Total Productos", "3", Modifier.weight(1f))
                    VerticalDivider(color = Color.Black, thickness = 1.dp)
                    SummaryItem("Valor Total Stock", "15'350.000.00", Modifier.weight(1f))
                }
                HorizontalDivider(color = Color.Black, thickness = 1.dp)
                // Fila Inferior
                Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                    SummaryItem("Listas Realizadas", "1", Modifier.weight(1f))
                    VerticalDivider(color = Color.Black, thickness = 1.dp)
                    SummaryItem("Clientes", "2", Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. BOTONES DE MENÚ ---
            val menuOptions = listOf("Inventario", "Clientes", "Tendencias", "Informes")

            menuOptions.forEach { option ->
                MenuButton(option) {
                    when (option) {
                        "Inventario" -> {
                            navController.navigate("inventarioStats")
                        }
                        "Clientes" -> {
                            navController.navigate("clienteStats")
                        }
                        "Tendencias" -> {
                            navController.navigate("tendencias")
                        }
                        "Informes" -> {

                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                    color = Color.Gray,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
fun TimeChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) PrincipalPurple else LightPurple
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(Icons.Default.Stars, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 14.sp)
    }
}

@Composable
fun SummaryItem(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, color = TextPurple, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = value, color = TextPurple, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)
    }
}

@Composable
fun MenuButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .height(55.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrincipalPurple),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Icon(Icons.Default.KeyboardDoubleArrowRight, contentDescription = null, tint = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KairoTopBar(title: String, navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrincipalPurple
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Volver",
                    tint = PrincipalPurple
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}