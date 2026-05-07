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
import com.example.stockmaster.model.Informe
import com.example.stockmaster.viewmodel.InformeViewModel

@Composable
fun CrearInformeScreen(
    viewModel: InformeViewModel,
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {

    var nombre by remember { mutableStateOf("") }

    var tipo by remember {
        mutableStateOf("Inventario")
    }

    var incluirGraficas by remember {
        mutableStateOf(true)
    }

    var incluirRanking by remember {
        mutableStateOf(false)
    }

    var incluirRecomendaciones by remember {
        mutableStateOf(false)
    }

    var formato by remember {
        mutableStateOf("PDF")
    }

    var error by remember {
        mutableStateOf("")
    }

    val loading = viewModel.loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        // HEADER

        TextButton(onClick = onBack) {
            Text("← Volver")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Crear Informe",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // NOMBRE

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                error = ""
            },
            label = {
                Text("Nombre del informe")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // TIPO

        Text(
            text = "Tipo de informe",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        val tipos = listOf(
            "Inventario",
            "Clientes",
            "Tendencias"
        )

        tipos.forEach { item ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {

                RadioButton(
                    selected = tipo == item,
                    onClick = {
                        tipo = item
                    }
                )

                Text(
                    text = item,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // OPCIONES

        Text(
            text = "Opciones",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        OpcionCheck(
            title = "Incluir gráficas",
            checked = incluirGraficas,
            onCheckedChange = {
                incluirGraficas = it
            }
        )

        OpcionCheck(
            title = "Incluir ranking",
            checked = incluirRanking,
            onCheckedChange = {
                incluirRanking = it
            }
        )

        OpcionCheck(
            title = "Incluir recomendaciones",
            checked = incluirRecomendaciones,
            onCheckedChange = {
                incluirRecomendaciones = it
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // FORMATO

        Text(
            text = "Formato",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row {

            AssistChip(
                onClick = {
                    formato = "PDF"
                },
                label = {
                    Text("PDF")
                }
            )

            Spacer(modifier = Modifier.width(12.dp))

            AssistChip(
                onClick = {
                    formato = "Excel"
                },
                label = {
                    Text("Excel")
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ERROR

        if (error.isNotEmpty()) {

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // BOTÓN

        Button(
            onClick = {

                if (nombre.isBlank()) {
                    error = "Ingresa un nombre"
                    return@Button
                }

                val informe = Informe(
                    nombre = nombre,
                    tipo = tipo,
                    incluirGraficas = incluirGraficas,
                    incluirRanking = incluirRanking,
                    incluirRecomendaciones = incluirRecomendaciones,
                    formato = formato
                )

                viewModel.crearInforme(
                    informe = informe,
                    onSuccess = {
                        onSuccess()
                    },
                    onError = {
                        error = it
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            enabled = !loading
        ) {

            if (loading) {

                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )

            } else {

                Text("Generar Informe")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun OpcionCheck(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        Text(
            text = title,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}
