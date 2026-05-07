package com.example.stockmaster.ui.screens.finanzas

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Colores de identidad del Proyecto KAIRO
val BackgroundGray = Color(0xFFF5F5F5)

@Composable
fun InventarioStatsScreen(navController: NavController) {

    val scrollState = rememberScrollState()

    Scaffold(

        // ==================== TOP BAR ====================

        topBar = {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // ===== FLECHA ATRAS =====

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

                    Spacer(modifier = Modifier.width(8.dp))

                    // ===== TITULO =====

                    Text(
                        text = "Movimiento de Inventario",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrincipalPurple,
                        modifier = Modifier.weight(1f)
                    )

                    // ===== BOTON REFRESH =====

                    IconButton(
                        onClick = { /* Refresh data */ }
                    ) {

                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = null,
                            tint = PrincipalPurple,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {

            // ==================== GRAFICO ====================

            Text(
                text = "Flujo de Inventario Semanal",
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp)
                    .background(
                        BackgroundGray,
                        RoundedCornerShape(8.dp)
                    ),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    "Espacio para Gráfico de Líneas",
                    color = Color.Gray
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // ==================== DISTRIBUCION ====================

            HeaderSection(
                "Distribucion por Categoria",
                Icons.Default.Public
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                // ===== PIE CHART =====

                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(
                            LightPurple,
                            CircleShape
                        ),

                    contentAlignment = Alignment.Center
                ) {

                    Canvas(
                        modifier = Modifier.size(140.dp)
                    ) {

                        drawArc(
                            color = PrincipalPurple,
                            startAngle = 0f,
                            sweepAngle = 180f,
                            useCenter = true
                        )

                        drawArc(
                            color = Color(0xFF8E87FF),
                            startAngle = 180f,
                            sweepAngle = 90f,
                            useCenter = true
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(start = 24.dp)
                ) {

                    CategoryLabel("Smartphones 50%")
                    CategoryLabel("Equipos de Sonido 25%")
                    CategoryLabel("Audifonos 15%")
                    CategoryLabel("Portatiles 10%")
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // ==================== STOCK MOVIDO ====================

            RankingSection(
                title = "Stock Movido",

                items = listOf(
                    "Smartphones" to "120",
                    "Equipos de sonido" to "60",
                    "Audifonos" to "28",
                    "Portatiles" to "24"
                ),

                icon = Icons.Default.Inventory2
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // ==================== STOCK LENTO ====================

            RankingSection(
                title = "Stock Lento",

                items = listOf(
                    "Fundas" to "5",
                    "Cargadores" to "8"
                ),

                icon = Icons.Default.ThumbDown
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // ==================== ALERTAS ====================

            AlertRow(
                Icons.Default.LocalFireDepartment,
                "Alta demanda:",
                "Smartphone",
                "120 Und",
                Color(0xFFFF5722)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            AlertRow(
                Icons.Default.Warning,
                "Bajo stock:",
                "Equipos de Sonido",
                "10 Und",
                Color(0xFFFFC107)
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // ==================== RECOMENDACION ====================

            RecomendacionCard()

            Spacer(
                modifier = Modifier.height(32.dp)
            )
        }
    }
}

// ==================== HEADER ====================

@Composable
fun HeaderSection(
    title: String,
    icon: ImageVector
) {

    Row(
        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrincipalPurple
        )

        Icon(
            icon,
            contentDescription = null,
            tint = PrincipalPurple,
            modifier = Modifier.size(28.dp)
        )
    }
}

// ==================== CATEGORY LABEL ====================

@Composable
fun CategoryLabel(text: String) {

    Text(
        text = text,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPurple,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

// ==================== RANKING SECTION ====================

@Composable
fun RankingSection(
    title: String,
    items: List<Pair<String, String>>,
    icon: ImageVector
) {

    Text(
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = PrincipalPurple
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = LightPurple
            ),

            modifier = Modifier.weight(1f),

            elevation = CardDefaults.cardElevation(6.dp),

            shape = RoundedCornerShape(12.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                items.forEachIndexed { index, item ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),

                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "${index + 1}. ${item.first}",
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = item.second,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        Icon(
            icon,
            contentDescription = null,
            tint = PrincipalPurple,
            modifier = Modifier.size(70.dp)
        )
    }
}

// ==================== ALERT ROW ====================

@Composable
fun AlertRow(
    icon: ImageVector,
    label: String,
    product: String,
    amount: String,
    iconColor: Color
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(50.dp)
                .border(
                    2.dp,
                    PrincipalPurple,
                    RoundedCornerShape(8.dp)
                ),

            contentAlignment = Alignment.Center
        ) {

            Icon(
                Icons.Default.PhoneAndroid,
                contentDescription = null,
                tint = PrincipalPurple
            )
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = " $label",
                    color = PrincipalPurple,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = product,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPurple
                )

                Text(
                    text = amount,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPurple
                )
            }
        }
    }
}

// ==================== RECOMENDACION ====================

@Composable
fun RecomendacionCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(12.dp),

        border = BorderStroke(
            1.dp,
            Color.LightGray
        ),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = PrincipalPurple
                )

                Text(
                    text = " Recomendacion!",
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    color = PrincipalPurple,
                    fontSize = 16.sp
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = "Reabastécete de Equipos de Sonido",
                color = TextPurple,
                fontSize = 15.sp,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Button(
                onClick = { /* Acción */ },

                colors = ButtonDefaults.buttonColors(
                    containerColor = PrincipalPurple
                ),

                shape = RoundedCornerShape(8.dp),

                modifier = Modifier.height(36.dp),

                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 0.dp
                )
            ) {

                Text(
                    "SI!!!",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}