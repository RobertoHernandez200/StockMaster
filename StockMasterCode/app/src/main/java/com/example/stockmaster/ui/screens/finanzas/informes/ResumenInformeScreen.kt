package com.example.stockmaster.ui.screens.finanzas.informes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stockmaster.viewmodel.InformeViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DetalleInformeScreen(
    informeId: String,
    viewModel: InformeViewModel,
    onBack: () -> Unit
) {

    val informe = viewModel.informeSeleccionado
    val loading = viewModel.loading

    LaunchedEffect(Unit) {
        viewModel.cargarInforme(informeId)
    }

    if (loading) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    if (informe == null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("No se pudo cargar el informe")
        }

        return
    }

    val formatter = SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.getDefault()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("← Volver")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = informe.nombre,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tipo: ${informe.tipo}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = formatter.format(Date(informe.fecha)),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Opciones del informe",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("• Gráficas: ${if (informe.incluirGraficas) "Sí" else "No"}")
                Text("• Ranking: ${if (informe.incluirRanking) "Sí" else "No"}")
                Text("• Recomendaciones: ${if (informe.incluirRecomendaciones) "Sí" else "No"}")

                Spacer(modifier = Modifier.height(12.dp))

                Text("Formato: ${informe.formato}")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Resumen",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Total productos: ${informe.totalProductos}")
                Text("Valor total: $${informe.valorTotal}")
            }
        }
    }
}