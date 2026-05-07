package com.example.stockmaster.model

data class Informe(

    val id: String = "",

    val nombre: String = "",

    val tipo: String = "",

    val fecha: Long = System.currentTimeMillis(),

    val fechaInicio: String = "",
    val fechaFin: String = "",

    val incluirGraficas: Boolean = false,
    val incluirRanking: Boolean = false,
    val incluirRecomendaciones: Boolean = false,

    val exportarPDF: Boolean = true,
    val exportarExcel: Boolean = false,

    val totalProductos: Int = 0,
    val valorTotal: Double = 0.0,

    val topProductos: List<String> = emptyList(),

    val formato: String = ""
)