package com.example.stockmaster.data.remote

import com.example.stockmaster.data.ResultadoOperacion
import com.example.stockmaster.model.Producto
import java.util.UUID

class ProductosRepository {

    private val listaProductos = mutableListOf<Producto>()

    fun crearProducto(
        nombre: String,
        valor: Double,
        stock: Int,
        imagenBytes: ByteArray
    ): ResultadoOperacion {

        val nuevo = Producto(
            id = UUID.randomUUID().toString(),
            nombre = nombre,
            valor = valor,
            stock = stock
        )

        listaProductos.add(nuevo)

        return ResultadoOperacion.Exito
    }

    fun eliminarProducto(id: String): ResultadoOperacion {

        val eliminado = listaProductos.removeIf { it.id == id }

        return if (eliminado) {
            ResultadoOperacion.Exito
        } else {
            ResultadoOperacion.Error("No se pudo eliminar")
        }
    }
}