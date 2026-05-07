package com.example.stockmaster.data.remote

import com.example.stockmaster.model.Informe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InformeRepository {

    private val db = FirebaseFirestore.getInstance()

    private val auth = FirebaseAuth.getInstance()

    fun crearInforme(
        informe: Informe,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val userId = auth.currentUser?.uid ?: return

        val id = db.collection("usuarios")
            .document(userId)
            .collection("informes")
            .document()
            .id

        db.collection("usuarios")
            .document(userId)
            .collection("informes")
            .document(id)
            .set(informe.copy(id = id))
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun obtenerInformes(
        onSuccess: (List<Informe>) -> Unit,
        onError: (String) -> Unit
    ) {

        val userId = auth.currentUser?.uid ?: return

        db.collection("usuarios")
            .document(userId)
            .collection("informes")
            .get()
            .addOnSuccessListener { result ->

                val lista = result.documents.mapNotNull {
                    it.toObject(Informe::class.java)
                }

                onSuccess(lista)
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun obtenerInformePorId(
        id: String,
        onSuccess: (Informe) -> Unit,
        onError: (String) -> Unit
    ) {

        val userId = auth.currentUser?.uid ?: return

        db.collection("usuarios")
            .document(userId)
            .collection("informes")
            .document(id)
            .get()
            .addOnSuccessListener { document ->

                val informe = document.toObject(Informe::class.java)

                if (informe != null) {
                    onSuccess(informe)
                } else {
                    onError("No se encontró el informe")
                }
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun eliminarInforme(
        informeId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val userId = auth.currentUser?.uid ?: return

        db.collection("usuarios")
            .document(userId)
            .collection("informes")
            .document(informeId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error eliminando informe")
            }
    }
}