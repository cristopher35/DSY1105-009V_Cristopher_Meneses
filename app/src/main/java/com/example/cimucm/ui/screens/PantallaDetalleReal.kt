package com.example.cimucm.ui.screens

import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cimucm.data.DatosSimulados
import com.example.cimucm.ui.theme.*
import com.example.cimucm.viewmodel.FavoritosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleReal(
    tipo: String,
    id: String,
    favoritosViewModel: FavoritosViewModel,
    alVolver: () -> Unit,
    onIrContacto: (String) -> Unit
) {
    val contexto = LocalContext.current
    val proyecto = DatosSimulados.proyectos.find { it.id == id }
    val esFav = favoritosViewModel.esFavorito(id)

    // Animación de color del botón de favorito (IE 2.2.2)
    val colorFavAnimado by animateColorAsState(
        targetValue = if (esFav) ColorError else Blanco,
        animationSpec = tween(durationMillis = 300),
        label = "colorFavoritoAnim"
    )

    // Animación de escala/tamaño al presionar favorito (IE 2.2.2)
    val tamanoIcono by animateDpAsState(
        targetValue = if (esFav) 28.dp else 24.dp,
        animationSpec = tween(durationMillis = 200),
        label = "tamanoFavoritoAnim"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Proyecto", color = Blanco, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = alVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Blanco)
                    }
                },
                actions = {
                    // Recurso Nativo 1: Compartir por Intent de Android (IE 2.4.1)
                    IconButton(onClick = {
                        val textoACompartir = "Revisa este proyecto de CIM UC: ${proyecto?.titulo}\nLínea: ${proyecto?.lineaInvestigacion}"
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, textoACompartir)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, "Compartir proyecto vía:")
                        contexto.startActivity(shareIntent)
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Compartir", tint = Blanco)
                    }

                    // Botón con animación funcional para marcar/desmarcar favoritos
                    IconButton(onClick = { favoritosViewModel.alternarFavorito(id) }) {
                        Icon(
                            imageVector = if (esFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = colorFavAnimado,
                            modifier = Modifier.size(tamanoIcono)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdePrincipal)
            )
        }
    ) { padding ->
        if (proyecto == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Proyecto no encontrado.")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Etiqueta de la línea de investigación
                Surface(
                    color = VerdeClaro,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = proyecto.lineaInvestigacion,
                        color = VerdePrincipal,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = proyecto.titulo,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Blanco),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Líder de Investigación: ${proyecto.lider}", fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Año de inicio: ${proyecto.anio}", color = CafeSecundario)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Descripción del Proyecto",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = CafeSecundario
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = proyecto.descripcionCompleta,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Botón para contactar directamente por este proyecto
                Button(
                    onClick = { onIrContacto(proyecto.titulo) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = VerdePrincipal),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Solicitar Contacto / Colaboración", fontSize = 16.sp, color = Blanco)
                }
            }
        }
    }
}