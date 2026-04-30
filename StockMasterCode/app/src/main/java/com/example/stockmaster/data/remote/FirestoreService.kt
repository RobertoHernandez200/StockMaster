package com.example.stockmaster.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService {

    private val db = FirebaseFirestore.getInstance()

    suspend fun obtenerTiendaPorCodigo(codigo: String): String? {

        return try {

            val result = db.collection("usuarios") //
                .whereEqualTo("codigo", codigo)
                .whereEqualTo("role", "tienda") // 
                .get()
                .await()

            if (!result.isEmpty) {
                result.documents.first().id
            } else {
                null
            }

        } catch (e: Exception) {
            null
        }
    }
}