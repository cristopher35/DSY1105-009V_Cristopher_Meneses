package com.example.cimucm.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

// Rutas e iconos para las 4 pestañas de la barra inferior
sealed class DestinoBarra(val ruta: String, val titulo: String, val icono: ImageVector) {
    object Inicio : DestinoBarra("inicio", "Inicio", Icons.Default.Home)
    object Buscar : DestinoBarra("buscar", "Buscar", Icons.Default.Search)
    object Oportunidades : DestinoBarra("oportunidades", "Oportunidades", Icons.Default.Star)
    object Favoritos : DestinoBarra("favoritos", "Favoritos", Icons.Default.Favorite)
}

// Lista para iterar los botones de la barra inferior
val listaDestinosBarra = listOf(
    DestinoBarra.Inicio,
    DestinoBarra.Buscar,
    DestinoBarra.Oportunidades,
    DestinoBarra.Favoritos
)