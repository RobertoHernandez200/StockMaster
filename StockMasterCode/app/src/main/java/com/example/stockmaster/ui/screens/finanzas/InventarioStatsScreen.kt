package com.example.stockmaster.ui.screens.finanzas
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stockmaster.viewmodel.InventarioStatsViewModel

// Colores de identidad del Proyecto KAIRO
val BackgroundGray = Color(0xFFF5F5F5)

@Composable
fun InventarioStatsScreen(
    navController: NavController,
    viewModel: InventarioStatsViewModel
) {

    val scrollState = rememberScrollState()

    val productos by viewModel.productos.collectAsState(initial = emptyList())

    val totalProductos by viewModel.totalProductos.collectAsState(initial = 0)

    val valorTotal by viewModel.valorTotal.collectAsState(initial = 0.0)

    val topProductos by viewModel.topProductos.collectAsState(initial = emptyList())

    val stockBajo by viewModel.stockBajo.collectAsState(initial = emptyList())

    val categorias by viewModel.categorias.collectAsState(initial = emptyMap())

    val altaDemanda by viewModel.altaDemanda.collectAsState(initial = null)

    val menorStock by viewModel.menorStock.collectAsState(initial = null)

    Scaffold(

        topBar = {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

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

                    Text(
                        text = "Movimiento de Inventario",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrincipalPurple,
                        modifier = Modifier.weight(1f)
                    )
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

            // RESUMEN

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LightPurple
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Resumen General",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Total productos: $totalProductos",
                        color = Color.White
                    )

                    Text(
                        text = "Valor total: $${"%.2f".format(valorTotal)}",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // CATEGORÍAS

            HeaderSection(
                "Distribución por Categoría",
                Icons.Default.Public
            )

            Spacer(modifier = Modifier.height(12.dp))

            categorias.forEach { (categoria, cantidad) ->

                CategoryLabel(
                    "$categoria - $cantidad productos"
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // TOP PRODUCTOS

            RankingSection(
                title = "Productos con Más Stock",
                items = topProductos,
                icon = Icons.Default.Inventory2
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // STOCK BAJO

            RankingSection(
                title = "Stock Bajo",
                items = stockBajo,
                icon = Icons.Default.Warning
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // ALERTA DEMANDA

            altaDemanda?.let {

                AlertRow(
                    icon = Icons.Default.LocalFireDepartment,
                    label = "Mayor Stock:",
                    product = it.nombre,
                    amount = "${it.stock} Und",
                    iconColor = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ALERTA STOCK BAJO

            menorStock?.let {

                AlertRow(
                    icon = Icons.Default.Warning,
                    label = "Menor Stock:",
                    product = it.nombre,
                    amount = "${it.stock} Und",
                    iconColor = Color(0xFFFFC107)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // RECOMENDACIÓN

            menorStock?.let {

                RecomendacionCard(
                    producto = it.nombre,

                    onReabastecerClick = {

                        navController.navigate(
                            "reabastecer/${it.nombre}"
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
// ==================== HEADER SECTION ====================

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
            imageVector = icon,
            contentDescription = null,
            tint = PrincipalPurple,
            modifier = Modifier.size(28.dp)
        )
    }
}
// ==================== CATEGORY LABEL ====================

@Composable
fun CategoryLabel(
    text: String
) {

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

    Spacer(modifier = Modifier.height(12.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier.weight(1f),

            colors = CardDefaults.cardColors(
                containerColor = LightPurple
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),

            shape = RoundedCornerShape(12.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                if (items.isEmpty()) {

                    Text(
                        text = "Sin datos",
                        color = Color.White
                    )

                } else {

                    items.forEachIndexed { index, item ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),

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
        }

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = icon,
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
                imageVector = Icons.Default.Inventory2,
                contentDescription = null,
                tint = PrincipalPurple
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = icon,
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

            Spacer(modifier = Modifier.height(4.dp))

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
fun RecomendacionCard(
    producto: String,
    onReabastecerClick: () -> Unit
) {

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
                    text = " Recomendación",
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    color = PrincipalPurple,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Reabastécete de $producto",
                color = TextPurple,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onReabastecerClick,

                colors = ButtonDefaults.buttonColors(
                    containerColor = PrincipalPurple
                ),

                shape = RoundedCornerShape(8.dp)
            ) {

                Text(
                    text = "Reabastecer",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}