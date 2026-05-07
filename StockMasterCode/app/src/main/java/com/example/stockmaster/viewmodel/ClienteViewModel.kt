package com.example.stockmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmaster.data.remote.FirestoreService
import com.example.stockmaster.model.Tienda
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClienteViewModel : ViewModel() {

    private val firestore = FirestoreService()
    private val auth = FirebaseAuth.getInstance()

    private val _tiendas = MutableStateFlow<List<Tienda>>(emptyList())
    val tiendas: StateFlow<List<Tienda>> = _tiendas

    private val _tienda = MutableStateFlow<Tienda?>(null)
    val tienda: StateFlow<Tienda?> = _tienda

    private val _listas = MutableStateFlow<List<Map<String, String>>>(emptyList())
    val listas: StateFlow<List<Map<String, String>>> = _listas

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    init {
        cargarTiendas()
        cargarListas()
    }

    fun buscarTienda(codigo: String) {
        viewModelScope.launch {
            val result = firestore.obtenerTiendaPorCodigo(codigo)

            if (result != null) {
                _tienda.value = result
                _error.value = null
            } else {
                _error.value = "Código inválido"
                _tienda.value = null
            }
        }
    }

    fun confirmarTienda() {
        viewModelScope.launch {

            val userId = auth.currentUser?.uid ?: return@launch
            val tienda = _tienda.value ?: return@launch

            val existe = firestore.tiendaYaExiste(userId, tienda.id)

            if (existe) {
                _error.value = "La tienda ya está agregada"
                return@launch
            }

            firestore.guardarTiendaCliente(userId, tienda)

            _success.value = true
            cargarTiendas()
        }
    }

    fun eliminarTienda(tiendaId: String) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            firestore.eliminarTiendaCliente(userId, tiendaId)
            cargarTiendas()
        }
    }

    fun cargarTiendas() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            _tiendas.value = firestore.obtenerTiendasCliente(userId)
        }
    }

    fun guardarLista(nombre: String, tiendaId: String, productos: List<String>) {
        viewModelScope.launch {

            val userId = auth.currentUser?.uid ?: return@launch

            val lista = hashMapOf(
                "nombre" to nombre,
                "tiendaId" to tiendaId,
                "productos" to productos.joinToString(",")
            )

            firestore.guardarListaDeseos(userId, lista)
            cargarListas()
        }
    }

    fun cargarListas() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            _listas.value = firestore.obtenerListasDeseos(userId)
        }
    }

    fun editarNombreLista(
        listaId: String,
        nuevoNombre: String
    ) {

        viewModelScope.launch {

            val userId = auth.currentUser?.uid ?: return@launch

            val listaActual = _listas.value.find {
                it["id"] == listaId
            } ?: return@launch

            val nuevaLista = listaActual.toMutableMap()

            nuevaLista["nombre"] = nuevoNombre

            firestore.actualizarLista(
                userId,
                listaId,
                nuevaLista
            )

            cargarListas()
        }
    }

    fun actualizarProductosLista(
        listaId: String,
        productos: List<String>
    ) {

        viewModelScope.launch {

            val userId = auth.currentUser?.uid ?: return@launch

            val listaActual = _listas.value.find {
                it["id"] == listaId
            } ?: return@launch

            val nuevaLista = listaActual.toMutableMap()

            nuevaLista["productos"] =
                productos.joinToString(",")

            firestore.actualizarLista(
                userId,
                listaId,
                nuevaLista
            )

            cargarListas()
        }
    }

    fun eliminarLista(listaId: String) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            firestore.eliminarLista(userId, listaId)
            cargarListas()
        }
    }

    fun eliminarProductoDeLista(listaId: String, producto: String) {
        viewModelScope.launch {

            val userId = auth.currentUser?.uid ?: return@launch

            val listaActual = _listas.value.find { it["id"] == listaId } ?: return@launch

            val productos = listaActual["productos"]
                ?.split(",")
                ?.toMutableList() ?: mutableListOf()

            productos.remove(producto)

            val nuevaLista = listaActual.toMutableMap()
            nuevaLista["productos"] = productos.joinToString(",")

            firestore.actualizarLista(userId, listaId, nuevaLista)
            cargarListas()
        }
    }

    fun limpiar() {
        _tienda.value = null
        _error.value = null
        _success.value = false
    }
}