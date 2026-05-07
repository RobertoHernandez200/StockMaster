package com.example.stockmaster.data.remote

import com.example.stockmaster.model.Informe
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class InformeRepository {

    private val db = FirebaseFirestore.getInstance()

    fun crearInforme(
        informe: Informe,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val id = db.collection("informes").document().id

        db.collection("informes")
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

        db.collection("informes")
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

        db.collection("informes")
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


}