package com.example.stockmaster.data.remote

import com.example.stockmaster.model.Tienda
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService {

    private val db = FirebaseFirestore.getInstance()

    suspend fun obtenerTiendaPorCodigo(codigo: String): Tienda? {

        return try {

            val result = db.collection("usuarios")
                .whereEqualTo("codigo", codigo)
                .whereEqualTo("role", "tienda")
                .get()
                .await()

            if (!result.isEmpty) {

                val doc = result.documents.first()

                Tienda(
                    id = doc.id,
                    nombre = doc.getString("nombre") ?: "",
                    codigo = doc.getString("codigo") ?: ""
                )

            } else {
                null
            }

        } catch (e: Exception) {
            null
        }
    }
    suspend fun guardarTiendaCliente(userId: String, tienda: com.example.stockmaster.model.Tienda) {

        db.collection("clientes")
            .document(userId)
            .collection("tiendas")
            .document(tienda.id)
            .set(tienda)
            .await()
    }
}

