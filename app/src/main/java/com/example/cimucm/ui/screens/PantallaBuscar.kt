package com.example.cimucm.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cimucm.model.Proyecto
import com.example.cimucm.ui.theme.Blanco
import com.example.cimucm.ui.theme.CafeSecundario
import com.example.cimucm.ui.theme.VerdeClaro
import com.example.cimucm.ui.theme.VerdePrincipal
import com.example.cimucm.viewmodel.BuscarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBuscar(
    viewModel: BuscarViewModel,
    onNavegarDetalle: (String, String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar", fontWeight = FontWeight.Bold, color = Blanco) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdePrincipal)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.textoBusqueda,
                onValueChange = { viewModel.onTextoBusquedaChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar proyectos, líneas...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                },
                trailingIcon = {
                    if (viewModel.textoBusqueda.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onTextoBusquedaChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filtro: ${viewModel.filtroSeleccionado}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = CafeSecundario
                )
                FilledTonalButton(
                    onClick = { viewModel.setMostrarFiltrosEstado(true) },
                    colors = ButtonDefaults.filledTonalButtonColors(containerColor = VerdeClaro)
                ) {
                    Icon(imageVector = Icons.Default.FilterList, contentDescription = null, tint = VerdePrincipal)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Filtros", color = VerdePrincipal)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val resultados = viewModel.proyectosFiltrados

            if (resultados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontraron proyectos.")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(resultados) { proyecto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavegarDetalle("proyecto", proyecto.id) },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Blanco),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = proyecto.lineaInvestigacion.uppercase(),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VerdePrincipal
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = proyecto.titulo,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = proyecto.descripcionCorta,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // ModalBottomSheet (P03 Filtros)
        if (viewModel.mostrarFiltros) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.setMostrarFiltrosEstado(false) },
                containerColor = Blanco
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Filtros",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdePrincipal
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    val lineas = listOf(
                        "Todas",
                        "Edificación en Altura",
                        "Sustentabilidad y Clima",
                        "Tecnología de Materiales"
                    )

                    lineas.forEach { linea ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.onFiltroSeleccionado(linea) }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (linea == viewModel.filtroSeleccionado),
                                onClick = { viewModel.onFiltroSeleccionado(linea) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = linea, fontSize = 15.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}