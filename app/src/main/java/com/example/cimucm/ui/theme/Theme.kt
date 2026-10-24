package com.example.cimucm.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Esquema de colores Material 3
private val LightColorScheme = lightColorScheme(
    primary = VerdePrincipal,
    onPrimary = Blanco,
    primaryContainer = VerdeClaro,
    onPrimaryContainer = VerdePrincipal,
    secondary = CafeSecundario,
    onSecondary = Blanco,
    background = FondoApp,
    onBackground = TextoOscuro,
    surface = FondoApp,
    onSurface = TextoOscuro,
    error = ColorError,
    onError = Blanco
)

@Composable
fun CIMUCMTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}