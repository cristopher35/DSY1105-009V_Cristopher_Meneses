package com.example.cimucm.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cimucm.data.DatosSimulados
import com.example.cimucm.model.Proyecto

// ViewModel que administra la búsqueda y filtros desacoplados de la interfaz
class BuscarViewModel : ViewModel() {
    var textoBusqueda by mutableStateOf("")
        private set

    var filtroSeleccionado by mutableStateOf("Todas")
        private set

    var mostrarFiltros by mutableStateOf(false)
        private set

    fun onTextoBusquedaChange(nuevoTexto: String) {
        textoBusqueda = nuevoTexto
    }

    fun onFiltroSeleccionado(nuevoFiltro: String) {
        filtroSeleccionado = nuevoFiltro
        mostrarFiltros = false
    }

    fun setMostrarFiltrosEstado(mostrar: Boolean) {
        mostrarFiltros = mostrar
    }

    val proyectosFiltrados: List<Proyecto>
        get() {
            return DatosSimulados.proyectos.filter { proyecto ->
                val coincideTexto = proyecto.titulo.contains(textoBusqueda, ignoreCase = true) ||
                        proyecto.descripcionCorta.contains(textoBusqueda, ignoreCase = true)
                val coincideFiltro = if (filtroSeleccionado == "Todas") true else proyecto.lineaInvestigacion == filtroSeleccionado
                coincideTexto && coincideFiltro
            }
        }
}