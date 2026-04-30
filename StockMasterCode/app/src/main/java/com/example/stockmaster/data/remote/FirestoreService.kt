package com.example.stockmaster.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService {

    private val db = FirebaseFirestore.getInstance()

    suspend fun obtenerTiendaPorCodigo(codigo: String): String? {
        val result = db.collection("tiendas")
            .whereEqualTo("codigo", codigo)
            .get()
            .await()

        return if (!result.isEmpty) {
            result.documents.first().id
        } else {
            null
        }
    }
}