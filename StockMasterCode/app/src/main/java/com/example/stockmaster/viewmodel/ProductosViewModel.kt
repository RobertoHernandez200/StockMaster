package com.example.stockmaster.viewmodel

import androidx.lifecycle.ViewModel
import com.example.stockmaster.model.Producto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductosViewModel : ViewModel() {

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Estados UI
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _valor = MutableStateFlow("")
    val valor: StateFlow<String> = _valor

    private val _stock = MutableStateFlow("")
    val stock: StateFlow<String> = _stock

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private var productoId: String? = null

    // Se ejecuta al entrar a la pantalla
    init {
        cargarProductos()
    }

    // CARGAR PRODUCTOS DEL USUARIO
    private fun cargarProductos() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("usuarios")
            .document(userId)
            .collection("productos")
            .addSnapshotListener { snapshot, _ ->

                if (snapshot != null) {
                    val lista = snapshot.documents.mapNotNull {
                        it.toObject(Producto::class.java)?.copy(id = it.id)
                    }
                    _productos.value = lista
                }
            }
    }

    // Inputs
    fun onNombreChange(value: String) {
        _nombre.value = value
    }

    fun onValorChange(value: String) {
        _valor.value = value
    }

    fun onStockChange(value: String) {
        _stock.value = value
    }

    // Cargar producto para editar
    fun cargarProducto(producto: Producto) {
        productoId = producto.id
        _nombre.value = producto.nombre
        _valor.value = producto.valor.toString()
        _stock.value = producto.stock.toString()
    }

    // CREAR O EDITAR PRODUCTO
    fun crearProducto() {

        val userId = auth.currentUser?.uid ?: return

        val valorDouble = _valor.value.toDoubleOrNull()
        val stockInt = _stock.value.toIntOrNull()

        if (_nombre.value.isBlank() || valorDouble == null || stockInt == null) {
            return
        }

        val producto = hashMapOf(
            "nombre" to _nombre.value,
            "valor" to valorDouble,
            "stock" to stockInt
        )

        if (productoId == null) {
            // CREAR
            db.collection("usuarios")
                .document(userId)
                .collection("productos")
                .add(producto)

        } else {
            // EDITAR
            db.collection("usuarios")
                .document(userId)
                .collection("productos")
                .document(productoId!!)
                .set(producto)

            productoId = null
        }

        limpiarCampos()
    }

    // ELIMINAR
    fun eliminarProducto(id: String) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("usuarios")
            .document(userId)
            .collection("productos")
            .document(id)
            .delete()
    }

    // Limpiar inputs
    private fun limpiarCampos() {
        _nombre.value = ""
        _valor.value = ""
        _stock.value = ""
    }
}