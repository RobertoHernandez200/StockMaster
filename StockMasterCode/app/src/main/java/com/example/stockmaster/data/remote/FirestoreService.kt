package com.example.stockmaster.data.remote

import com.example.stockmaster.model.Tienda
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService {

    val db = FirebaseFirestore.getInstance()

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

            } else null

        } catch (e: Exception) {
            null
        }
    }

    suspend fun guardarTiendaCliente(userId: String, tienda: Tienda) {
        db.collection("clientes")
            .document(userId)
            .collection("tiendas")
            .document(tienda.id)
            .set(tienda)
            .await()
    }

    suspend fun tiendaYaExiste(userId: String, tiendaId: String): Boolean {
        val doc = db.collection("clientes")
            .document(userId)
            .collection("tiendas")
            .document(tiendaId)
            .get()
            .await()

        return doc.exists()
    }

    suspend fun obtenerTiendasCliente(userId: String): List<Tienda> {
        val result = db.collection("clientes")
            .document(userId)
            .collection("tiendas")
            .get()
            .await()

        return result.documents.map {
            Tienda(
                id = it.id,
                nombre = it.getString("nombre") ?: "",
                codigo = it.getString("codigo") ?: ""
            )
        }
    }

    suspend fun eliminarTiendaCliente(userId: String, tiendaId: String) {
        db.collection("clientes")
            .document(userId)
            .collection("tiendas")
            .document(tiendaId)
            .delete()
            .await()
    }

    // 🔥 GUARDAR LISTA
    suspend fun guardarListaDeseos(
        userId: String,
        nombre: String,
        tiendaId: String,
        productos: List<String>
    ) {
        val lista = hashMapOf(
            "nombre" to nombre,
            "tiendaId" to tiendaId,
            "productos" to productos
        )

        db.collection("clientes")
            .document(userId)
            .collection("listas")
            .add(lista)
            .await()
    }

    // 🔥 OBTENER LISTAS CON ID
    suspend fun obtenerListasDeseos(userId: String): List<Map<String, Any>> {

        val result = db.collection("clientes")
            .document(userId)
            .collection("listas")
            .get()
            .await()

        return result.documents.mapNotNull { doc ->
            doc.data?.toMutableMap()?.apply {
                put("id", doc.id)
            }
        }
    }

    // 🔥 ELIMINAR LISTA
    suspend fun eliminarLista(userId: String, listaId: String) {
        db.collection("clientes")
            .document(userId)
            .collection("listas")
            .document(listaId)
            .delete()
            .await()
    }
}