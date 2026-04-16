package com.example.stockmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmaster.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductosViewModel : ViewModel() {

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _valor = MutableStateFlow("")
    val valor: StateFlow<String> = _valor

    private val _stock = MutableStateFlow("")
    val stock: StateFlow<String> = _stock

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private var productoId: String? = null

    fun onNombreChange(value: String) {
        _nombre.value = value
    }

    fun onValorChange(value: String) {
        _valor.value = value
    }

    fun onStockChange(value: String) {
        _stock.value = value
    }

    fun cargarProducto(producto: Producto) {
        productoId = producto.id
        _nombre.value = producto.nombre
        _valor.value = producto.valor.toString()
        _stock.value = producto.stock.toString()
    }

    fun crearProducto() {
        viewModelScope.launch {

            val valorDouble = _valor.value.toDoubleOrNull()
            val stockInt = _stock.value.toIntOrNull()

            if (_nombre.value.isBlank() || valorDouble == null || stockInt == null) {
                return@launch
            }

            if (productoId == null) {

                val nuevo = Producto(
                    id = System.currentTimeMillis().toString(),
                    nombre = _nombre.value,
                    valor = valorDouble,
                    stock = stockInt
                )

                _productos.value = _productos.value + nuevo

            } else {

                _productos.value = _productos.value.map {
                    if (it.id == productoId) {
                        it.copy(
                            nombre = _nombre.value,
                            valor = valorDouble,
                            stock = stockInt
                        )
                    } else it
                }

                productoId = null
            }

            _nombre.value = ""
            _valor.value = ""
            _stock.value = ""
        }
    }

    fun eliminarProducto(id: String) {
        viewModelScope.launch {
            _productos.value = _productos.value.filterNot { it.id == id }
        }
    }
}