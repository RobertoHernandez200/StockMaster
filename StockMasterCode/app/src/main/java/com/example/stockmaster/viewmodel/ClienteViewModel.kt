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

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    init {
        cargarTiendas()
    }

    fun cargarTiendas() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            _tiendas.value = firestore.obtenerTiendasCliente(userId)
        }
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

    fun limpiar() {
        _tienda.value = null
        _error.value = null
        _success.value = false
    }
}