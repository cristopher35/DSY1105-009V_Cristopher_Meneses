package com.example.cimucm.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cimucm.data.DatosSimulados
import com.example.cimucm.model.Proyecto
import com.example.cimucm.ui.theme.Blanco
import com.example.cimucm.ui.theme.CafeSecundario
import com.example.cimucm.ui.theme.VerdeClaro
import com.example.cimucm.ui.theme.VerdePrincipal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(
    onNavegarDetalle: (String, String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CIMUC Móvil", fontWeight = FontWeight.Bold, color = Blanco) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdePrincipal)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Banner de bienvenida
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = VerdeClaro),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Centro de Innovación en Madera",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = VerdePrincipal
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Explora capacidades, proyectos destacados y oportunidades de colaboración científica.",
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Sección Proyectos Destacados (Carrusel Horizontal)
            item {
                Text(
                    text = "Proyectos Destacados",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = CafeSecundario
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(DatosSimulados.proyectos) { proyecto ->
                        TarjetaProyecto(proyecto = proyecto, onClick = {
                            onNavegarDetalle("proyecto", proyecto.id)
                        })
                    }
                }
            }

            // Sección Líneas de Investigacion y Desarrollo
            item {
                Text(
                    text = "Líneas de Investigacion y Desarrollo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = CafeSecundario
                )
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "• Ingeniería estructural y sísmica en madera")
                        Text(text = "• Sustentabilidad y eficiencia energética")
                        Text(text = "• Nuevos bioproductos y nanotecnología")
                    }
                }
            }
        }
    }
}

// Componente reusable para la tarjeta de cada proyecto
@Composable
fun TarjetaProyecto(proyecto: Proyecto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Blanco),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = proyecto.lineaInvestigacion.uppercase(),
                fontSize = 11.sp,
                color = VerdePrincipal,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = proyecto.titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = proyecto.descripcionCorta,
                fontSize = 13.sp,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Año: ${proyecto.anio}",
                fontSize = 12.sp,
                color = CafeSecundario
            )
        }
    }
}