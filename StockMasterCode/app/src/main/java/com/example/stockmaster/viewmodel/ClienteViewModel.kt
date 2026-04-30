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

    private val _listas = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val listas: StateFlow<List<Map<String, Any>>> = _listas

    init {
        cargarTiendas()
        cargarListas()
    }

    fun guardarLista(nombre: String, tiendaId: String, productos: List<String>) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            firestore.guardarListaDeseos(userId, nombre, tiendaId, productos)
            cargarListas()
        }
    }

    fun cargarListas() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            _listas.value = firestore.obtenerListasDeseos(userId)
        }
    }

    fun eliminarLista(listaId: String) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            firestore.eliminarLista(userId, listaId)
            cargarListas()
        }
    }

    fun cargarTiendas() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            _tiendas.value = firestore.obtenerTiendasCliente(userId)
        }
    }
}