package com.example.stockmaster.viewmodel

sealed class EstadoUI {
    object Idle : EstadoUI()
    object Loading : EstadoUI()
    data class Exito(val mensaje: String) : EstadoUI()
    data class Error(val mensaje: String) : EstadoUI()
}