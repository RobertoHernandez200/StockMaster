package com.example.stockmaster.data

sealed class ResultadoOperacion {
    object Exito : ResultadoOperacion()
    data class Error(val mensaje: String) : ResultadoOperacion()
}