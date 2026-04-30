package com.example.stockmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmaster.model.Producto
import com.example.stockmaster.data.remote.FirestoreService
import com.example.stockmaster.model.Tienda
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClienteViewModel : ViewModel() {

    var tiendaSeleccionada: String? = null

    private val firestore = FirestoreService()
    private val auth = FirebaseAuth.getInstance()

    private val _tiendas = MutableStateFlow<List<Tienda>>(emptyList())
    val tiendas: StateFlow<List<Tienda>> = _tiendas

    private val _tienda = MutableStateFlow<Tienda?>(null)
    val tienda: StateFlow<Tienda?> = _tienda

    private val _listas = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val listas: StateFlow<List<Map<String, Any>>> = _listas

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _productosLista = MutableStateFlow<List<Producto>>(emptyList())
    val productosLista: StateFlow<List<Producto>> = _productosLista

    fun cargarProductosDeLista(listaId: String) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            _productosLista.value = firestore.obtenerProductosDeLista(userId, listaId)
        }
    }

    fun eliminarProductoDeLista(listaId: String, productoId: String) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            firestore.eliminarProductoDeLista(userId, listaId, productoId)
            cargarProductosDeLista(listaId)
        }
    }

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    init {
        cargarTiendas()
        cargarListas()
    }

    // 🔥 BUSCAR TIENDA POR CÓDIGO
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

    // 🔥 CONFIRMAR TIENDA
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

    // 🔥 ELIMINAR TIENDA (ESTO ES LO QUE TE FALTABA BIEN)
    fun eliminarTienda(tiendaId: String) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch

            firestore.eliminarTiendaCliente(userId, tiendaId)

            cargarTiendas()
        }
    }

    // 🔥 CARGAR TIENDAS
    fun cargarTiendas() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            _tiendas.value = firestore.obtenerTiendasCliente(userId)
        }
    }

    // 🔥 LISTAS
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

    fun eliminarLista(id: String) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            firestore.eliminarLista(userId, id)
            cargarListas()
        }
    }

    fun limpiar() {
        _tienda.value = null
        _error.value = null
        _success.value = false
    }
}