package com.example.stockmaster.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EmpleadoViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun crearEmpleado(
        nombre: String,
        email: String,
        password: String,
        permisos: Map<String, Boolean>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val adminUser = auth.currentUser

        if (adminUser == null) {
            onError("Admin no autenticado")
            return
        }

        val adminId = adminUser.uid
        val adminEmail = adminUser.email ?: ""

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->

                val empleadoId = result.user?.uid

                if (empleadoId == null) {
                    onError("Error creando usuario")
                    return@addOnSuccessListener
                }

                val empleado = hashMapOf(
                    "nombre" to nombre,
                    "email" to email,
                    "role" to "empleado",
                    "adminId" to adminId,
                    "permisos" to permisos
                )

                db.collection("usuarios")
                    .document(empleadoId)
                    .set(empleado)
                    .addOnSuccessListener {

                        // ⚠️ SOLO PARA PRUEBAS
                        val adminPassword = "123456"

                        auth.signInWithEmailAndPassword(adminEmail, adminPassword)
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener {
                                onError("Error re-autenticando admin")
                            }
                    }
                    .addOnFailureListener {
                        onError("Error guardando datos")
                    }
            }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Error desconocido")
            }
    }
}