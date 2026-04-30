package com.example.stockmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmaster.data.remote.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClienteViewModel : ViewModel() {

    private val firestore = FirestoreService()

    private val _tiendaId = MutableStateFlow<String?>(null)
    val tiendaId: StateFlow<String?> = _tiendaId

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun ingresarCodigo(codigo: String) {
        viewModelScope.launch {
            val result: String? = firestore.obtenerTiendaPorCodigo(codigo)

            if (result != null) {
                _tiendaId.value = result
            } else {
                _error.value = "Código inválido"
            }
        }
    }
}