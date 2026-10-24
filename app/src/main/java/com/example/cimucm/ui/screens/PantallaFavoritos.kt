package com.example.cimucm.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cimucm.data.DatosSimulados
import com.example.cimucm.ui.theme.Blanco
import com.example.cimucm.ui.theme.ColorError
import com.example.cimucm.ui.theme.VerdePrincipal
import com.example.cimucm.viewmodel.FavoritosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFavoritos(
    viewModel: FavoritosViewModel,
    onNavegarDetalle: (String, String) -> Unit
) {
    // Proyectos que coinciden con los IDs guardados en SharedPreferences (IE 2.3.1)
    val listaFavoritos = DatosSimulados.proyectos.filter { viewModel.esFavorito(it.id) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Favoritos Guardados", fontWeight = FontWeight.Bold, color = Blanco) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdePrincipal)
            )
        }
    ) { padding ->
        if (listaFavoritos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aún no has guardado proyectos en favoritos.\nToca el corazón en cualquier detalle para guardarlo.",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listaFavoritos) { proyecto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavegarDetalle("proyecto", proyecto.id) },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Blanco),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = proyecto.lineaInvestigacion.uppercase(),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VerdePrincipal
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = proyecto.titulo,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            IconButton(onClick = { viewModel.alternarFavorito(proyecto.id) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Quitar",
                                    tint = ColorError
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}