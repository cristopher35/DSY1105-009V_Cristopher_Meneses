package com.example.cimucm.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cimucm.navigation.DestinoBarra
import com.example.cimucm.navigation.listaDestinosBarra
import com.example.cimucm.ui.screens.*
import com.example.cimucm.ui.theme.VerdeClaro
import com.example.cimucm.ui.theme.VerdePrincipal
import com.example.cimucm.viewmodel.BuscarViewModel
import com.example.cimucm.viewmodel.ContactoViewModel
import com.example.cimucm.viewmodel.FavoritosViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    // ViewModels desacoplados para Búsqueda, Favoritos y Contacto (IE 2.2.1)
    val buscarViewModel: BuscarViewModel = viewModel()
    val favoritosViewModel: FavoritosViewModel = viewModel()
    val contactoViewModel: ContactoViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    val mostrarBarraInferior = listaDestinosBarra.any { it.ruta == rutaActual }

    Scaffold(
        bottomBar = {
            if (mostrarBarraInferior) {
                NavigationBar(containerColor = VerdeClaro) {
                    listaDestinosBarra.forEach { destino ->
                        val seleccionado = rutaActual == destino.ruta
                        NavigationBarItem(
                            selected = seleccionado,
                            onClick = {
                                navController.navigate(destino.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(destino.icono, contentDescription = destino.titulo) },
                            label = { Text(destino.titulo) },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = VerdePrincipal,
                                selectedIconColor = VerdeClaro,
                                selectedTextColor = VerdePrincipal
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValores ->
        NavHost(
            navController = navController,
            startDestination = DestinoBarra.Inicio.ruta,
            modifier = Modifier.padding(paddingValores)
        ) {
            // P01 Inicio
            composable(DestinoBarra.Inicio.ruta) {
                PantallaInicio(
                    onNavegarDetalle = { tipo, id ->
                        navController.navigate("detalle/$tipo/$id")
                    }
                )
            }

            // P02 Buscar y P03 Filtros
            composable(DestinoBarra.Buscar.ruta) {
                PantallaBuscar(
                    viewModel = buscarViewModel,
                    onNavegarDetalle = { tipo, id ->
                        navController.navigate("detalle/$tipo/$id")
                    }
                )
            }

            // P05 Oportunidades
            composable(DestinoBarra.Oportunidades.ruta) {
                PantallaOportunidades(
                    onPostular = { asunto ->
                        contactoViewModel.iniciarConAsunto(asunto)
                        navController.navigate("contacto")
                    }
                )
            }

            // P08 Favoritos con Persistencia Local (SharedPreferences)
            composable(DestinoBarra.Favoritos.ruta) {
                PantallaFavoritos(
                    viewModel = favoritosViewModel,
                    onNavegarDetalle = { tipo, id ->
                        navController.navigate("detalle/$tipo/$id")
                    }
                )
            }

            // P04 Detalle Real
            composable(
                route = "detalle/{tipo}/{id}",
                arguments = listOf(
                    navArgument("tipo") { type = NavType.StringType },
                    navArgument("id") { type = NavType.StringType }
                )
            ) { entrada ->
                val tipo = entrada.arguments?.getString("tipo") ?: ""
                val id = entrada.arguments?.getString("id") ?: ""
                PantallaDetalleReal(
                    tipo = tipo,
                    id = id,
                    favoritosViewModel = favoritosViewModel,
                    alVolver = { navController.popBackStack() },
                    onIrContacto = { tituloProyecto ->
                        contactoViewModel.iniciarConAsunto(tituloProyecto)
                        navController.navigate("contacto")
                    }
                )
            }

            // P06 Contacto
            composable("contacto") {
                PantallaContacto(
                    viewModel = contactoViewModel,
                    alVolver = { navController.popBackStack() },
                    onEnvioExitoso = {
                        navController.navigate("confirmacion")
                    }
                )
            }

            // P07 Confirmación
            composable("confirmacion") {
                PantallaConfirmacion(
                    onVolverInicio = {
                        contactoViewModel.limpiarFormulario()
                        navController.navigate(DestinoBarra.Inicio.ruta) {
                            popUpTo(DestinoBarra.Inicio.ruta) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}