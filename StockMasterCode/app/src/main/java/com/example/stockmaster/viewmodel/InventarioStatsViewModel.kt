package com.example.stockmaster.viewmodel

import androidx.lifecycle.ViewModel
import com.example.stockmaster.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InventarioStatsViewModel : ViewModel() {

    private val productosViewModel = ProductosViewModel()

    val productos = productosViewModel.productos

    private val _totalProductos = MutableStateFlow(0)
    val totalProductos: StateFlow<Int> = _totalProductos.asStateFlow()

    private val _valorTotal = MutableStateFlow(0.0)
    val valorTotal: StateFlow<Double> = _valorTotal.asStateFlow()

    private val _topProductos =
        MutableStateFlow<List<Pair<String, String>>>(emptyList())

    val topProductos = _topProductos.asStateFlow()

    private val _stockBajo =
        MutableStateFlow<List<Pair<String, String>>>(emptyList())

    val stockBajo = _stockBajo.asStateFlow()

    private val _categorias =
        MutableStateFlow<Map<String, Int>>(emptyMap())

    val categorias = _categorias.asStateFlow()

    private val _altaDemanda =
        MutableStateFlow<Producto?>(null)

    val altaDemanda = _altaDemanda.asStateFlow()

    private val _menorStock =
        MutableStateFlow<Producto?>(null)

    val menorStock = _menorStock.asStateFlow()

    init {

        productosViewModel.productos.value.let { lista ->

            actualizarDatos(lista)
        }
    }

    private fun actualizarDatos(
        lista: List<Producto>
    ) {

        _totalProductos.value = lista.size

        _valorTotal.value =
            lista.sumOf { it.valor * it.stock }

        _topProductos.value =
            lista.sortedByDescending { it.stock }
                .take(5)
                .map {
                    it.nombre to "${it.stock}"
                }

        _stockBajo.value =
            lista.filter { it.stock <= 5 }
                .map {
                    it.nombre to "${it.stock}"
                }

        _categorias.value =
            lista.groupingBy {
                it.categoria.ifBlank { "Sin categoría" }
            }.eachCount()

        _altaDemanda.value =
            lista.maxByOrNull { it.stock }

        _menorStock.value =
            lista.minByOrNull { it.stock }
    }
}