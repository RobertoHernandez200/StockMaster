package com.example.stockmaster.ui.screens.finanzas

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ClientesStatsScreen(navController: NavController) {

    val scrollState = rememberScrollState()

    var selectedTab by remember {
        mutableStateOf("Frecuentes")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
            .padding(16.dp)
    ) {

        // ==================== TOP BAR ====================

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
                    tint = PrincipalPurple,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Clientes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PrincipalPurple
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ==================== RESUMEN GENERAL ====================

        Text(
            "Resumen General",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrincipalPurple
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SummaryCard(
                Modifier.weight(0.4f),
                "2",
                "Clientes",
                Icons.Default.PersonOutline
            )

            SummaryCard(
                Modifier.weight(0.6f),
                "Ingresos",
                "$ 5’000.000.00",
                Icons.Default.TrendingUp
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        // ==================== GRAFICO ====================

        Text(
            "Compras por cliente",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrincipalPurple
        )

        Text(
            "Volumen de Compras por Cliente",
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(vertical = 16.dp),

            contentAlignment = Alignment.BottomCenter
        ) {

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {

                BarChartItem(
                    "KostoMarket",
                    0.9f,
                    PrincipalPurple,
                    "$2,850,000"
                )

                BarChartItem(
                    "SuperAhorro",
                    0.7f,
                    LightPurple,
                    "$2,150,000"
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        // ==================== RANKING ====================

        Text(
            "¡Aqui los clientes que mas te quieren!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = PrincipalPurple,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        RankingCard(
            listOf(
                "1. KostoMarket" to "$2’850.000.00",
                "2. SuperAhorro" to "$2’150.000.00"
            )
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        // ==================== TIPO CLIENTES ====================

        Text(
            "¿Que tipo de clientes tienes?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = PrincipalPurple,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),

            horizontalArrangement = Arrangement.Center
        ) {

            FilterChipItem(
                "Frecuentes",
                selectedTab == "Frecuentes"
            ) {
                selectedTab = "Frecuentes"
            }

            FilterChipItem(
                "Ocasionales",
                selectedTab == "Ocasionales"
            ) {
                selectedTab = "Ocasionales"
            }

            FilterChipItem(
                "Nuevos",
                selectedTab == "Nuevos"
            ) {
                selectedTab = "Nuevos"
            }
        }

        RankingCard(
            listOf(
                "1. KostoMarket" to "2 Compras",
                "2. SuperAhorro" to "1 Compra"
            )
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        // ==================== ALERTAS ====================

        InsightItem(
            "Cliente Estrella :",
            "KostoMarket",
            "🔥"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                "😨",
                fontSize = 60.sp
            )

            Text(
                "No tienes clientes Nuevos!\nCuidado con eso",
                fontSize = 18.sp,
                color = PrincipalPurple,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                "¡Promociona tus productos!\nO\n¡Generales Descuentos!",
                fontSize = 18.sp,
                color = PrincipalPurple,
                fontWeight = FontWeight.Bold
            )

            Icon(
                Icons.Default.Chat,
                contentDescription = null,
                tint = PrincipalPurple,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

// ==================== SUMMARY CARD ====================

@Composable
fun SummaryCard(
    modifier: Modifier,
    topText: String,
    bottomText: String,
    icon: ImageVector
) {

    Card(
        modifier = modifier.height(120.dp),

        shape = RoundedCornerShape(12.dp),

        colors = CardDefaults.cardColors(
            containerColor = LightPurple
        ),

        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    topText,
                    fontSize = 24.sp,
                    color = Color.White
                )

                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                bottomText,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==================== BAR CHART ====================

@Composable
fun BarChartItem(
    label: String,
    heightPercentage: Float,
    color: Color,
    amount: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            amount,
            fontSize = 10.sp,
            color = PrincipalPurple,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .width(100.dp)
                .fillMaxHeight(heightPercentage)
                .background(
                    color,
                    RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 4.dp
                    )
                )
        )

        Text(
            label,
            fontSize = 10.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// ==================== RANKING CARD ====================

@Composable
fun RankingCard(data: List<Pair<String, String>>) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(12.dp),

        colors = CardDefaults.cardColors(
            containerColor = LightPurple
        ),

        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            data.forEach { item ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),

                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        item.first,
                        fontSize = 20.sp,
                        color = Color.White
                    )

                    Text(
                        item.second,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

// ==================== FILTER CHIP ====================

@Composable
fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable { onClick() },

        shape = RoundedCornerShape(16.dp),

        color = if (isSelected)
            PrincipalPurple
        else
            LightPurple
    ) {

        Row(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                Icons.Default.Stars,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )

            Text(
                " $label",
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

// ==================== INSIGHT ITEM ====================

@Composable
fun InsightItem(
    label: String,
    name: String,
    emoji: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {

            Text(
                label,
                fontSize = 20.sp,
                color = PrincipalPurple,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                name,
                fontSize = 22.sp,
                color = PrincipalPurple,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Text(
            emoji,
            fontSize = 60.sp
        )
    }
}