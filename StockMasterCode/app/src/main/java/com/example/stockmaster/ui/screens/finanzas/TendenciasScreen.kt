package com.example.stockmaster.ui.screens.finanzas

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

val UltraLightPurple = Color(0xFFD1CFFF)

@Composable
fun TendenciasScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var selectedTime by remember { mutableStateOf("Hoy") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
            .padding(16.dp)
    ) {
        // --- 1. HEADER Y SELECTORES ---
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ArrowBackIosNew, contentDescription = null, tint = PrincipalPurple, modifier = Modifier.size(20.dp))
            Text(" Tendencias", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = PrincipalPurple)
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TimeFilterChip("Hoy", selectedTime == "Hoy") { selectedTime = "Hoy" }
            TimeFilterChip("Semana", selectedTime == "Semana") { selectedTime = "Semana" }
            TimeFilterChip("Mes", selectedTime == "Mes") { selectedTime = "Mes" }
        }

        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        // --- 2. PRODUCTOS MÁS DESEADOS (GRÁFICO) ---
        Text("Productos mas deseados", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrincipalPurple, modifier = Modifier.padding(top = 12.dp))
        Text("Top Productos Deseados por Clientes", fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally))

        Box(modifier = Modifier.fillMaxWidth().height(220.dp).padding(vertical = 16.dp), contentAlignment = Alignment.BottomCenter) {
            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                TrendBar("Smartphone", 0.9f, PrincipalPurple, "1500")
                TrendBar("Equipos", 0.7f, Color(0xFF8E87FF), "1100")
                TrendBar("Audifonos", 0.5f, LightPurple, "850")
                TrendBar("Portatiles", 0.35f, UltraLightPurple, "600")
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        // --- 3. PRODUCTOS ARDIENDO ---
        Text("Estos productos estan ardiendo🔥", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = PrincipalPurple)
        Spacer(modifier = Modifier.height(8.dp))
        RankingCardWithIcons(listOf("Smartphone" to "50 veces", "Equipos de Sonido" to "20 veces", "Audifonos" to "18 veces"))

        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))

        // --- 4. LO QUE QUIEREN VS LO QUE TIENES ---
        Text("Lo que quieren vs Lo que tienes", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = PrincipalPurple)
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Favorite, contentDescription = null, tint = PrincipalPurple, modifier = Modifier.size(20.dp))
            Text(" vs ", color = PrincipalPurple, fontWeight = FontWeight.Bold)
            Icon(Icons.Default.Inventory2, contentDescription = null, tint = PrincipalPurple, modifier = Modifier.size(20.dp))
        }

        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ComparisonCard(Modifier.weight(1f), listOf("1. Smartphone", "2. Equipos de Sonido", "3. Audifonos"))
            ComparisonCard(Modifier.weight(0.8f), listOf("50  vs  20", "20  vs  12", "18  vs  8"), isData = true)
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        // --- 5. TENDENCIA EN EL TIEMPO ---
        Text("Tendencia en el Tiempo", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrincipalPurple, modifier = Modifier.align(Alignment.CenterHorizontally))
        Text("Tendencia de Popularidad Mensual", fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally))

        // Simulación de gráfico de área
        Box(modifier = Modifier.fillMaxWidth().height(180.dp).padding(vertical = 12.dp).background(Color(0xFFF8F8FF))) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Aquí dibujarías la línea con drawPath
            }
            Text("95%", modifier = Modifier.align(Alignment.TopEnd).padding(end = 10.dp), color = PrincipalPurple, fontWeight = FontWeight.Bold)
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))

        // --- 6. RECOMENDACIÓN FINAL ---
        RecommendationActionCard()

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun TimeFilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.padding(horizontal = 4.dp).clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) PrincipalPurple else LightPurple
    ) {
        Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Stars, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            Text(" $label", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TrendBar(label: String, heightPercentage: Float, color: Color, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 10.sp, color = PrincipalPurple, fontWeight = FontWeight.Bold)
        Box(modifier = Modifier.width(60.dp).fillMaxHeight(heightPercentage).background(color, RoundedCornerShape(4.dp)))
        Text(label, fontSize = 9.sp, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun RankingCardWithIcons(items: List<Pair<String, String>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LightPurple)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            items.forEachIndexed { index, item ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("${index + 1}. ${item.first}", fontSize = 20.sp, color = Color.White)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(24.dp))
                        Text(" ${item.second}", fontSize = 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ComparisonCard(modifier: Modifier, lines: List<String>, isData: Boolean = false) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = LightPurple)
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = if (isData) Alignment.CenterHorizontally else Alignment.Start) {
            lines.forEach { line ->
                Text(line, color = Color.White, fontSize = 16.sp, modifier = Modifier.padding(vertical = 2.dp))
            }
        }
    }
}

@Composable
fun RecommendationActionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = null, tint = PrincipalPurple)
                Text(" Recomendacion!", modifier = Modifier.padding(start = 8.dp), fontWeight = FontWeight.Bold, color = PrincipalPurple)
                Spacer(Modifier.weight(1f))
                Icon(Icons.Default.Close, contentDescription = null, tint = Color.Gray)
            }
            Text(
                "Los smartphones estan ardiendo, ten cuidado necesitas mas stock!!!",
                color = TextPurple, modifier = Modifier.padding(vertical = 12.dp), fontSize = 16.sp
            )
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = PrincipalPurple),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Añadir stock", fontWeight = FontWeight.Bold)
            }
        }
    }
}