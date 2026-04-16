package com.example.stockmaster.model

data class Producto(
    val id: String = "",
    val nombre: String = "",
    val valor: Double = 0.0,
    val stock: Int = 0
)