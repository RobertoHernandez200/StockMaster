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

            } else null

        } catch (e: Exception) {
            null
        }
    }

    //  GUARDAR TIENDA
    suspend fun guardarTiendaCliente(userId: String, tienda: Tienda) {

        db.collection("clientes")
            .document(userId)
            .collection("tiendas")
            .document(tienda.id)
            .set(tienda)
            .await()
    }

    //  VALIDAR SI YA EXISTE
    suspend fun tiendaYaExiste(userId: String, tiendaId: String): Boolean {

        val doc = db.collection("clientes")
            .document(userId)
            .collection("tiendas")
            .document(tiendaId)
            .get()
            .await()

        return doc.exists()
    }

    //  OBTENER TIENDAS
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

    //  ELIMINAR TIENDA
    suspend fun eliminarTiendaCliente(userId: String, tiendaId: String) {

        db.collection("clientes")
            .document(userId)
            .collection("tiendas")
            .document(tiendaId)
            .delete()
            .await()
    }

    // =========================================
    //  LISTAS DE DESEOS
    // =========================================

    //  GUARDAR LISTA
    suspend fun guardarListaDeseos(
        userId: String,
        lista: Map<String, String>
    ) {
        db.collection("clientes")
            .document(userId)
            .collection("listas")
            .add(lista)
            .await()
    }

    //  OBTENER LISTAS (ARREGLADO)
    suspend fun obtenerListasDeseos(userId: String): List<Map<String, String>> {

        val result = db.collection("clientes")
            .document(userId)
            .collection("listas")
            .get()
            .await()

        return result.documents.map {

            val data = it.data ?: emptyMap()

            //  AQUÍ ESTÁ LA SOLUCIÓN DEL ERROR
            val mapaConvertido = data.mapValues { entry ->
                entry.value?.toString() ?: ""
            }

            mapaConvertido + ("id" to it.id)
        }
    }

    //  ELIMINAR LISTA
    suspend fun eliminarLista(userId: String, listaId: String) {
        db.collection("clientes")
            .document(userId)
            .collection("listas")
            .document(listaId)
            .delete()
            .await()
    }

    //  ACTUALIZAR LISTA
    suspend fun actualizarLista(
        userId: String,
        listaId: String,
        lista: Map<String, String>
    ) {
        db.collection("clientes")
            .document(userId)
            .collection("listas")
            .document(listaId)
            .set(lista)
            .await()
    }
}