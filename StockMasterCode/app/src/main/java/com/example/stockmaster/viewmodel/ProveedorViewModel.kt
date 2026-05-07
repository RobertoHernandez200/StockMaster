package com.example.stockmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmaster.data.remote.FirestoreService
import com.example.stockmaster.model.Proveedor
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProveedorViewModel : ViewModel() {

    private val firestore = FirestoreService()
    private val auth = FirebaseAuth.getInstance()

    private val _proveedores =
        MutableStateFlow<List<Proveedor>>(emptyList())

    val proveedores: StateFlow<List<Proveedor>> =
        _proveedores

    init {
        cargarProveedores()
    }

    fun cargarProveedores() {

        viewModelScope.launch {

            val userId =
                auth.currentUser?.uid ?: return@launch

            _proveedores.value =
                firestore.obtenerProveedores(userId)
        }
    }

    fun agregarProveedor(
        nombre: String,
        correo: String
    ) {

        viewModelScope.launch {

            val userId =
                auth.currentUser?.uid ?: return@launch

            firestore.guardarProveedor(
                userId,
                Proveedor(
                    nombre = nombre,
                    correo = correo
                )
            )

            cargarProveedores()
        }
    }

    fun eliminarProveedor(id: String) {

        viewModelScope.launch {

            val userId =
                auth.currentUser?.uid ?: return@launch

            firestore.eliminarProveedor(userId, id)

            cargarProveedores()
        }
    }

    fun editarProveedor(
        id: String,
        nombre: String,
        correo: String
    ) {

        viewModelScope.launch {

            val userId =
                auth.currentUser?.uid ?: return@launch

            firestore.actualizarProveedor(
                userId,
                Proveedor(
                    id = id,
                    nombre = nombre,
                    correo = correo
                )
            )

            cargarProveedores()
        }
    }
}