package com.example.stockmaster.ui.screens.home_tienda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.viewmodel.ProductosViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeTiendaScreen(
    onAddProduct: () -> Unit,
    onUsuarios: () -> Unit,
    onProveedores: () -> Unit,
    onLogout: () -> Unit,
    onFinanzas: () -> Unit
) {

    val auth = FirebaseAuth.getInstance()

    // 🔥 ViewModel
    val viewModel: ProductosViewModel = viewModel()

    val codigo by viewModel.codigo.collectAsState()

    // 🔥 SCROLL
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFF5F5F5))
    ) {

        // ================= HEADER =================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement = Arrangement.SpaceBetween,

            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "Inicio",
                fontSize = 20.sp
            )

            TextButton(
                onClick = {
                    auth.signOut()
                    onLogout()
                }
            ) {

                Text("Salir")
            }
        }

        // ================= RESUMEN =================

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "HOY",
                fontSize = 14.sp
            )

            Text(
                text = "$0.00",
                fontSize = 32.sp
            )

            Text(
                text = "Código: $codigo",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }

        // ================= CONTENEDOR MORADO =================

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6A5AE0),
                            Color(0xFF8E7CFF)
                        )
                    ),

                    shape = RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                // ================= PRODUCTOS =================

                CardBox(
                    title = "Productos",
                    subtitle = "Agregar nuevo producto",
                    buttonText = "Añadir producto",
                    onClick = onAddProduct
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ================= USUARIOS =================

                CardBox(
                    title = "Usuarios",
                    subtitle = "Crear nuevo usuario",
                    buttonText = "Agregar usuario",
                    onClick = onUsuarios
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ================= PROVEEDORES =================

                CardBox(
                    title = "Proveedores",
                    subtitle = "Añadir nuevo contacto",
                    buttonText = "Agregar contacto",
                    onClick = onProveedores
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ================= FINANZAS =================

                CardBox(
                    title = "Finanzas",
                    subtitle = "Ver estadísticas e informes",
                    buttonText = "Ver informes",
                    onClick = onFinanzas
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun CardBox(
    title: String,
    subtitle: String,
    buttonText: String,
    onClick: () -> Unit
) {

    Card(
        shape = RoundedCornerShape(28.dp),

        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE5E4E9)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.Start
        ) {

            // ================= ICONO =================

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        Color(0xFFC4C4C4),
                        RoundedCornerShape(12.dp)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            // ================= TEXTOS =================

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // ================= BOTON =================

            Button(
                onClick = onClick,

                shape = RoundedCornerShape(12.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A5D91)
                ),

                contentPadding = PaddingValues(
                    horizontal = 10.dp,
                    vertical = 4.dp
                ),

                modifier = Modifier.heightIn(min = 36.dp)
            ) {

                Text(
                    text = "+ $buttonText",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}