package com.example.stockmaster.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmaster.data.remote.InformeRepository
import com.example.stockmaster.model.Informe
import kotlinx.coroutines.launch

class InformeViewModel : ViewModel() {

    private val repository = InformeRepository()

    var loading by mutableStateOf(false)
        private set

    var informes by mutableStateOf<List<Informe>>(emptyList())
        private set

    var informeSeleccionado by mutableStateOf<Informe?>(null)
        private set

    init {
        cargarInformes()
    }

    fun cargarInformes() {

        loading = true

        repository.obtenerInformes(
            onSuccess = {
                informes = it
                loading = false
            },
            onError = {
                loading = false
            }
        )
    }

    fun crearInforme(
        informe: Informe,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        loading = true

        repository.crearInforme(
            informe = informe,
            onSuccess = {

                cargarInformes()

                loading = false
                onSuccess()
            },
            onError = {

                loading = false
                onError(it)
            }
        )
    }

    fun cargarInforme(
        id: String
    ) {

        loading = true

        repository.obtenerInformePorId(
            id = id,
            onSuccess = {

                informeSeleccionado = it
                loading = false
            },
            onError = {

                loading = false
            }
        )
    }

    fun eliminarInforme(informeId: String) {

        repository.eliminarInforme(
            informeId = informeId,

            onSuccess = {
                cargarInformes()
            },

            onError = {

            }
        )
    }


}